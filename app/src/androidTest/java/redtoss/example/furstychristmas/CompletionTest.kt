package redtoss.example.furstychristmas

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import redtoss.example.furstychristmas.util.DateUtil.season
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CompletionTest {
    companion object {
        private const val YEAR = 2021
        val koin = GlobalContext.get()
        private val testDate = LocalDate.of(YEAR, Month.DECEMBER, 25)

        @BeforeClass
        @JvmStatic
        fun beforeContent2021Test() {
            println("beforeContent2021Test")
            DateUtil.setDevDay(testDate)
            val dayCompletionStatusUseCase: DayCompletionStatusUseCase = koin.get()
            assert(dayCompletionStatusUseCase.isDataBaseSetupForSeason(testDate.season())) { "Database is not setup for $testDate" }
            repeat(24) { dayIndexOfMonth ->
                val dayOfMonth = dayIndexOfMonth + 1
                runBlocking(Dispatchers.IO) {
                    dayCompletionStatusUseCase.markDayAsNotDone(LocalDate.of(YEAR, Month.DECEMBER, dayOfMonth))
                }
            }
        }
    }

    var handler: Handler = Handler(Looper.getMainLooper())

    @Test
    fun complete1Day() {
        val firstValueReceived = CountDownLatch(1)
        val dayToMark = LocalDate.of(YEAR, Month.DECEMBER, 6)
        val simpleObserver = Observer<List<Day>> {
            Timber.d("simpleObserver: completionStatusUseCase.days = $it")
            firstValueReceived.countDown()
        }
        val completionStatusUseCase: DayCompletionStatusUseCase = koin.get()
        val days = completionStatusUseCase.getDaysToCompleteForSeason(testDate.season())
        handler.post { days.observeForever(simpleObserver) }
        firstValueReceived.await(2, TimeUnit.SECONDS)
        assert(days.value?.none { it.isDone } ?: false) { "None of the days should be done yet, but: ${days.value?.filter { it.isDone }} are done." }
        runBlocking(Dispatchers.IO) {
            completionStatusUseCase.markDayAsDone(dayToMark)
            delay(200) // since markDayAsDone is suspending and the value will be changed in the DB, a small delay is required.
            days.value.let { days ->
                assert(days != null) { "days should not be 'null'" }
                assert(days?.count { it.isDone } == 1) { "Exact one of the days should be done, but: ${days?.filter { it.isDone }} are done." }
                assert(days?.find { it.day.sameDayAs(dayToMark) }?.isDone == true) { "The day: $dayToMark is not done, but should be." }
            }
        }
        handler.post { days.removeObserver(simpleObserver) }
    }

    @Test
    fun complete2Days() {
        val firstValueReceived = CountDownLatch(1)
        val dayToMark = LocalDate.of(YEAR, Month.DECEMBER, 6)
        val dayToMark1 = LocalDate.of(YEAR, Month.DECEMBER, 1)
        val simpleObserver = Observer<List<Day>> {
            Timber.d("simpleObserver: completionStatusUseCase.days = $it")
            firstValueReceived.countDown()
        }
        val completionStatusUseCase: DayCompletionStatusUseCase = koin.get()
        val days = completionStatusUseCase.getDaysToCompleteForSeason(testDate.season())
        handler.post { days.observeForever(simpleObserver) }
        firstValueReceived.await(2, TimeUnit.SECONDS)
        assert(days.value?.none { it.isDone } ?: false) { "None of the days should be done yet, but: ${days.value?.filter { it.isDone }} are done." }
        runBlocking(Dispatchers.IO) {
            completionStatusUseCase.markDayAsDone(dayToMark)
            completionStatusUseCase.markDayAsDone(dayToMark1)
            delay(200) // since markDayAsDone is suspending and the value will be changed in the DB, a small delay is required.
            days.value.let { days ->
                assert(days != null) { "days should not be 'null'" }
                assert(days?.count { it.isDone } == 2) { "Exact two of the days should be done, but: ${days?.filter { it.isDone }} are done." }
                assert(days?.find { it.day.sameDayAs(dayToMark) }?.isDone == true) { "The day: $dayToMark is not done, but should be." }
                assert(days?.find { it.day.sameDayAs(dayToMark1) }?.isDone == true) { "The day: $dayToMark1 is not done, but should be." }
            }
        }
        handler.post { days.removeObserver(simpleObserver) }
    }

}
