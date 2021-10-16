package com.example.furstychristmas.day.domain.usecase

import com.example.furstychristmas.day.domain.model.DayCompleted
import com.example.furstychristmas.day.domain.repository.DayCompletionRepository
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