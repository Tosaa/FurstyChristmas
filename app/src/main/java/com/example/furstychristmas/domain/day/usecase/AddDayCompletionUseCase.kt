package com.example.furstychristmas.domain.day.usecase

import com.example.furstychristmas.domain.day.model.DayCompleted
import com.example.furstychristmas.domain.day.repository.DayCompletionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate

class AddDayCompletionUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    suspend fun addDefaultEntriesForDates(dates: List<LocalDate>) {
        withContext(Dispatchers.IO) {
            Timber.i("addDefaultEntriesForDates:$dates")
            dayCompletionRepository.createDays(dates.map { DayCompleted(it, false) }.toList())
        }
    }
}