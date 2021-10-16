package com.example.furstychristmas.day.domain.usecase

import com.example.furstychristmas.day.domain.model.DayCompleted
import com.example.furstychristmas.day.domain.repository.DayCompletionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class DayCompletionStatusUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    val isDatabaseSetup = dayCompletionRepository.isDatabaseSetupForThisYear

    val getDaysToComplete = dayCompletionRepository.allDaysToComplete

    suspend fun isDayDone(date: LocalDate): Boolean = withContext(Dispatchers.IO) {
        dayCompletionRepository.getDay(date)?.isDone == true
    }

    suspend fun markDayAsDone(date: LocalDate) = withContext(Dispatchers.IO) {
        dayCompletionRepository.updateDay(DayCompleted(date, true))
    }

}