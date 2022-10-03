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
                    delay(5_000)
                }
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
            assertTrue("DaysToComplete should be size of 24, but it is ${daysToComplete.size}", daysToComplete.size == 24 || daysToComplete.isEmpty())
            assertTrue("The days to complete should be from last year (2021) but the provided days are from ${daysToComplete.map { day -> day.day.year }}", daysToComplete.all { day -> day.day.year == 2021 })
            latch.countDown()
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
    fun testSetupInSummer() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.AUGUST, 25))

        val observer = Observer<List<Day>> {
            assertTrue("DaysToComplete should be size of 24, but it is ${it.size}", it.size == 24 || it.isEmpty())
            assertTrue("None of the days should be available, but ${it.any { day -> day.isAvailableOn(DateUtil.today()) }} is available.", it.count { day -> day.isAvailableOn(DateUtil.today()) } == 0)
            latch.countDown()
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
    fun testSetupOn1stOfDecember() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.DECEMBER, 1))

        val observer = Observer<List<Day>> {
            assertTrue("DaysToComplete should be size of 24, but it is ${it.size}", it.size == 24 || it.isEmpty())
            assertTrue("Only one of the Days should be available", it.count { day -> day.isAvailableOn(DateUtil.today()) } == 1)
            assertTrue("By default none of the exercises should be completed, but ${it.find { it.isDone }} is completed.", it.none { it.isDone })
            latch.countDown()
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
            assertTrue("DaysToComplete should be size of 24, but it is ${it.size}", it.size == 24 || it.isEmpty())
            latch.countDown()
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
    fun testSetupOn24thOfDecember() {
        val latch = CountDownLatch(2)
        DateUtil.setDevDay(LocalDate.of(2022, Month.DECEMBER, 24))

        val observer = Observer<List<Day>> {
            assertTrue("DaysToComplete should be size of 24, but it is ${it.size}", it.size == 24 || it.isEmpty())
            latch.countDown()
        }

        val dayCompletionStatusUseCase = koin.get<DayCompletionStatusUseCase>()
        assertTrue("Database is not setup", dayCompletionStatusUseCase.isDatabaseSetup)
        handler.post {
            dayCompletionStatusUseCase.getDaysToComplete.observeForever(observer)
        }
        latch.await(4, TimeUnit.SECONDS)
        handler.post { dayCompletionStatusUseCase.getDaysToComplete.removeObserver(observer) }
    }

}
