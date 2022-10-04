package redtoss.example.furstychristmas

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.get
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.model.isAvailableOn
import redtoss.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class SetupTest {

    var handler: Handler = Handler(Looper.getMainLooper())

    companion object {
        val koin = get()

        @BeforeClass
        fun before() {
            val databaseSetupLatch = CountDownLatch(1)
            val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
            val addDayCompletionUseCase = koin.get<AddDayCompletionUseCase>()

            println("Database will be prepared for tests")
            CoroutineScope(Dispatchers.IO).launch {
                listOf(2020, 2021, 2022, 2023).forEach { year ->
                    println("Add Entries for year: $year")
                    val days = IntRange(1, 24).map { day -> LocalDate.of(year, Month.DECEMBER, day) }.toList()
                    addDayCompletionUseCase.addDefaultEntriesForDates(days)
                }
                delay(5_000)
                databaseSetupLatch.countDown()
            }
            databaseSetupLatch.await(11, TimeUnit.SECONDS)
            assertTrue("Database is still not setup after 10 seconds", dayCompletionStatusUseCase.isDatabaseSetup)
        }
    }

    @Test
    fun testSetupOnFirstOfJanuary() {
        val latch = CountDownLatch(1)
        DateUtil.setDevDay(LocalDate.of(2022, Month.JANUARY, 1))

        val observer = Observer<List<Day>> { daysToComplete ->
            if (daysToComplete.isNotEmpty()) {
                assert24DaysAreProvided(daysToComplete)
                assertDaysAreFromYear(daysToComplete, 2021)
                assertAvailableDays(daysToComplete, 24)
                latch.countDown()
            } else {
                Timber.d("Observed days, but they are empty")
            }
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertDatabaseIsSetup(dayCompletionStatusUseCase)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

    @Test
    fun testSetupInSummer() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.AUGUST, 25))

        val observer = Observer<List<Day>> {
            if (it.isNotEmpty()) {
                assert24DaysAreProvided(it)
                assertAvailableDays(it, 0)
                latch.countDown()
            } else {
                Timber.d("Observed days, but they are empty")
            }
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertDatabaseIsSetup(dayCompletionStatusUseCase)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

    @Test
    fun testSetupOn1stOfDecember() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.DECEMBER, 1))

        val observer = Observer<List<Day>> {
            if (it.isNotEmpty()) {
                assert24DaysAreProvided(it)
                assertCompletedDays(it, 0)
                latch.countDown()
            } else {
                Timber.d("Observed days, but they are empty")
            }
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertTrue("Database is not setup", dayCompletionStatusUseCase.isDatabaseSetup)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

    @Test
    fun testSetupOn6thOfDecember() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.DECEMBER, 6))

        val observer = Observer<List<Day>> {
            if (it.isNotEmpty()) {
                assert24DaysAreProvided(it)
                assertAvailableDays(it, 6)
                latch.countDown()
            } else {
                Timber.d("Observed days, but they are empty")
            }
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertDatabaseIsSetup(dayCompletionStatusUseCase)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

    @Test
    fun testSetupOn24thOfDecember() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.DECEMBER, 24))

        val observer = Observer<List<Day>> {
            if (it.isNotEmpty()) {
                assert24DaysAreProvided(it)
                assertAvailableDays(it, 24)
                assertCompletedDays(it, 0)
                assertDaysAreFromYear(it, 2022)
                latch.countDown()
            } else {
                Timber.d("Observed days, but they are empty")
            }
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertDatabaseIsSetup(dayCompletionStatusUseCase)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

    fun assertDatabaseIsSetup(dayCompletionStatusUseCase: DayCompletionStatusUseCase) {
        assertTrue("Database should be setup", dayCompletionStatusUseCase.isDatabaseSetup)
    }

    fun assertAvailableDays(days: List<Day>, amount: Int) {
        val availableDays = days.filter { day -> day.isAvailableOn(DateUtil.today()) }
        assertTrue("Only $amount of the Days should be available, but ${availableDays.size} are", availableDays.size == amount)
    }

    fun assertCompletedDays(days: List<Day>, amount: Int) {
        val completedDays = days.filter { day -> day.isDone }
        assertTrue("Only $amount of the Days should be done, but ${completedDays.size} are", completedDays.size == amount)
    }

    fun assert24DaysAreProvided(days: List<Day>) {
        assertTrue("At any time 24 days should be availabe to be displayed, but ${days.size} are", days.size == 24)
    }

    fun assertDaysAreFromYear(days: List<Day>, year: Int) {
        val wrongDays = days.filter { it.day.year != year }
        assertTrue("All days should be from year: $year, but $wrongDays contain wrong dates: ${wrongDays.map { it.day.year }.toSet()}", wrongDays.isEmpty())
    }
}
