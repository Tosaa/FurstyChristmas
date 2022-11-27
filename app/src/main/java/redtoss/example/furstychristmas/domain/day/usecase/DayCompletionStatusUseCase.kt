package redtoss.example.furstychristmas.domain.day.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.repository.DayCompletionRepository
import timber.log.Timber
import java.time.LocalDate

class DayCompletionStatusUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    fun isDataBaseSetupForSeason(season: Int) = dayCompletionRepository.isDataBaseSetup(season - 1)

    fun getDaysToCompleteForSeason(season: Int) = dayCompletionRepository.getDaysToComplete(season - 1)

    suspend fun isDayDone(date: LocalDate): Boolean = withContext(Dispatchers.IO) {
        val isDone = dayCompletionRepository.getDay(date)?.isDone == true
        Timber.i("isDayDone:$date = $isDone")
        return@withContext isDone
    }

    suspend fun markDayAsDone(date: LocalDate) = withContext(Dispatchers.IO) {
        Timber.d("DayCompletionStatusUseCase markDayAsDone(): date = $date")
        dayCompletionRepository.updateDay(Day(date, true))
    }

    suspend fun markDayAsNotDone(date: LocalDate) = withContext(Dispatchers.IO) {
        Timber.d("DayCompletionStatusUseCase markDayAsNotDone(): date = $date")
        dayCompletionRepository.updateDay(Day(date, false))
    }

}
