package com.example.furstychristmas.repository.day

import com.example.furstychristmas.model.DayCompleted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddDayCompletionUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    suspend fun addDefaultEntriesForDates(dates: List<LocalDate>) {
        withContext(Dispatchers.IO) {
            dayCompletionRepository.createDays(dates.map { DayCompleted(it, false) }.toList())
        }
    }
}