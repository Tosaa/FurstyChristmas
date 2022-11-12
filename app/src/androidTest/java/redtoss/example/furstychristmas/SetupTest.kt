package redtoss.example.furstychristmas

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.get
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.model.isAvailableOn
import redtoss.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.persistence.DayDatabase
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.season
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class SetupTest {
    companion object {
        val koin = get()
        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        val addDayCompletionUseCase = koin.get<AddDayCompletionUseCase>()

        private val ioScope = CoroutineScope(Dispatchers.IO)
        private val testScope = CoroutineScope(Dispatchers.Main + Job())
        private var setupJob: Job? = null
        private suspend fun nukeDB() {
            Timber.v("nukeDB()")
            val database: DayDatabase = koin.get()
            val dayCompletionDatabase = database.dayCompletedDao()

            val allDays = dayCompletionDatabase.getDays()
            Timber.v("nukeDB(): Remove all entries in Database: ${allDays.joinToString()}")
            allDays.forEach {
                dayCompletionDatabase.deleteCard(it)
            }
            Timber.i("nukeDB(): All Entries in Database removed")
        }

        private suspend fun addEntriesForYears(years: List<Int>) {
            years.forEach { year ->
                Timber.v("addEntriesForYears(): Add Entries for year: $year")
                val days = IntRange(1, 24).map { day -> LocalDate.of(year, Month.DECEMBER, day) }.toList()
                addDayCompletionUseCase.addDefaultEntriesForDates(days)
            }
            Timber.i("addEntriesForYears(): Entries for years: ${years.joinToString()} added")
        }

        @BeforeClass
        @JvmStatic
        fun beforeSetupTest() {
            val databaseSetupLatch = CountDownLatch(1)
            setupJob?.cancel()
            setupJob = testScope.launch {
                Timber.v("beforeSetupTest()")

                Timber.v("beforeSetupTest(): Database will be prepared for tests")
                ioScope.launch { nukeDB() }.join()
                launch {
                    addEntriesForYears((2020..2022).toList())
                    databaseSetupLatch.countDown()
                }.join()
                assertTrue("Database is still not setup after 10 seconds", dayCompletionStatusUseCase.isDataBaseSetupForSeason(2021))
                assertTrue("Database is still not setup after 10 seconds", dayCompletionStatusUseCase.isDataBaseSetupForSeason(2022))
                Timber.i("beforeSetupTest(): Database is setup for tests")
            }
            databaseSetupLatch.await(11, TimeUnit.SECONDS)
            runBlocking {
                setupJob?.join()
            }
        }
    }

    var handler: Handler = Handler(Looper.getMainLooper())

    private fun runTestForValues(
        year: Int,
        month: Month,
        dayOfMonth: Int,
        expectedClickableDays: Int? = null,
        expectedCompletedDays: Int? = null,
        expectedYear: Int? = null
    ) {
        val date = LocalDate.of(year, month, dayOfMonth)
        Timber.v("runTestForValues(): Date=$date")
        val testLatch = CountDownLatch(1)
        val observerForDays = Observer<List<Day>> { daysToComplete ->
            if (daysToComplete.isNotEmpty()) {
                Timber.v("Observed days are not empty")
                assert24DaysAreProvided(daysToComplete)
                expectedYear?.let { assertDaysAreFromYear(daysToComplete, it) }
                expectedCompletedDays?.let { assertCompletedDays(daysToComplete, it) }
                expectedClickableDays?.let { assertAvailableDays(daysToComplete, it) }
                testLatch.countDown()
            } else {
                Timber.v("Observed days, but they are empty")
            }
        }
        val testRun = testScope.async {
            if (setupJob?.isCompleted != true) {
                Timber.v("wait for setupJob")
                setupJob?.join()
            }
            DateUtil.setDevDay(date)
            assertDatabaseIsSetup(dayCompletionStatusUseCase)
            handler.post {
                dayCompletionStatusUseCase.getDaysToCompleteForSeason(date.season()).observeForever(observerForDays)
            }
            true
        }
        runBlocking {
            assertTrue("Test did not run trough successfully", testRun.await())
        }
        testLatch.await(1, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToCompleteForSeason(date.season()).removeObserver(observerForDays) }
        assertTrue("Test did not run trough successfully", testLatch.count == 0L)
    }

    @Test
    fun testSetupOnFirstOfJanuary() {
        runTestForValues(2021, Month.JANUARY, 1, expectedClickableDays = 24, expectedCompletedDays = 0, expectedYear = 2020)
    }

    @Test
    fun testSetupInSummer() {
        runTestForValues(2022, Month.AUGUST, 25, expectedClickableDays = 24)
    }

    @Test
    fun testSeasonStart() {
        runTestForValues(2022, Month.OCTOBER, 1, expectedClickableDays = 0)
    }

    @Test
    fun testSetupOn1stOfDecember() {
        runTestForValues(2022, Month.DECEMBER, 1, expectedClickableDays = 1, expectedCompletedDays = 0, expectedYear = 2022)
    }

    @Test
    fun testSetupOn6thOfDecember() {
        runTestForValues(2022, Month.DECEMBER, 6, expectedClickableDays = 6, expectedCompletedDays = 0, expectedYear = 2022)
    }

    @Test
    fun testSetupOn24thOfDecember() {
        runTestForValues(2022, Month.DECEMBER, 24, expectedClickableDays = 24, expectedCompletedDays = 0, expectedYear = 2022)
    }

    fun assertDatabaseIsSetup(dayCompletionStatusUseCase: DayCompletionStatusUseCase) {
        Timber.v("assertDatabaseIsSetup()")
        assertTrue("Database should be setup", dayCompletionStatusUseCase.isDataBaseSetupForSeason(2021))
    }

    fun assertAvailableDays(days: List<Day>, amount: Int) {
        Timber.v("assertAvailableDays(): $amount")
        val availableDays = days.filter { day -> day.isAvailableOn(DateUtil.today()) }
        assertTrue("Only $amount of the Days should be available, but ${availableDays.size} are", availableDays.size == amount)
    }

    fun assertCompletedDays(days: List<Day>, amount: Int) {
        Timber.v("assertCompletedDays(): $amount")
        val completedDays = days.filter { day -> day.isDone }
        assertTrue("Only $amount of the Days should be done, but ${completedDays.size} are", completedDays.size == amount)
    }

    fun assert24DaysAreProvided(days: List<Day>) {
        Timber.v("assert24DaysAreProvided()")
        assertTrue("At any time 24 days should be available to be displayed, but ${days.size} are", days.size == 24)
    }

    fun assertDaysAreFromYear(days: List<Day>, year: Int) {
        Timber.v("assertDaysAreFromYear(): $year")
        val wrongDays = days.filter { it.day.year != year }
        assertTrue("All days should be from year: $year, but $wrongDays contain wrong dates: ${wrongDays.map { it.day.year }.toSet()}", wrongDays.isEmpty())
    }
}
