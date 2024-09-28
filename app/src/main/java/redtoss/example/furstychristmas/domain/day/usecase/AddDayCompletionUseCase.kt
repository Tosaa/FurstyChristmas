package redtoss.example.furstychristmas.domain.day.usecase

import java.time.LocalDate
import java.time.Month
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.repository.DayCompletionRepository
import timber.log.Timber

class AddDayCompletionUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    suspend fun addDefaultEntriesForSeason(season: Int) {
        withContext(Dispatchers.IO) {
            val dates = (1..24).map { dayOfMonth ->
                LocalDate.of(season - 1, Month.DECEMBER, dayOfMonth)
            }
            Timber.i("addDefaultEntriesForDates:$dates")
            dayCompletionRepository.createDays(dates.map { Day(it, false) }.toList())
        }
    }
}
