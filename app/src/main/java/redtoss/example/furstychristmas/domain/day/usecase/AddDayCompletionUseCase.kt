package redtoss.example.furstychristmas.domain.day.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.repository.DayCompletionRepository
import timber.log.Timber
import java.time.LocalDate

class AddDayCompletionUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    suspend fun addDefaultEntriesForDates(dates: List<LocalDate>) {
        withContext(Dispatchers.IO) {
            Timber.i("addDefaultEntriesForDates:$dates")
            dayCompletionRepository.createDays(dates.map { Day(it, false) }.toList())
        }
    }
}