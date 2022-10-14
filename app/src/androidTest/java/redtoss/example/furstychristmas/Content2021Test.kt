package redtoss.example.furstychristmas

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate
import java.time.Month


@RunWith(AndroidJUnit4::class)
class Content2021Test {
    companion object {
        val koin = GlobalContext.get()
        private val testDate = LocalDate.of(2021, Month.DECEMBER, 25)
        private val allDaysInDecember = (1..23).map { LocalDate.of(2021, Month.DECEMBER, it) }.toSet()
        val workoutsInDecember = listOf(1, 3, 6, 8, 10, 13, 15, 17, 20).map { LocalDate.of(2021, Month.DECEMBER, it) }.toSet()
        val infosInDecember = allDaysInDecember.minus(workoutsInDecember).toSet()

        @BeforeClass
        @JvmStatic
        fun beforeContent2021Test() {
            println("beforeContent2021Test")
            DateUtil.setDevDay(testDate)
        }
    }

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    @Test
    fun testAllDaysWithWorkouts() {
        val workoutUseCase = koin.get<LoadWorkoutUseCase>()
        runBlocking {
            backgroundScope.launch {
                workoutsInDecember.forEach { date ->
                    assertNotNull("There should be an existing workout at day: $date", workoutUseCase.getWorkoutAtDay(date))
                }
            }.join()
        }
    }

    @Test
    fun testAllDaysWithInfos() {
        val infoUseCase = koin.get<LoadInfoUseCase>()
        runBlocking {
            backgroundScope.launch {
                infosInDecember.forEach { date ->
                    assertNotNull("There should be an existing Info at day: $date", infoUseCase.getInfoAtDay(date))
                }
            }.join()
        }
    }
}
