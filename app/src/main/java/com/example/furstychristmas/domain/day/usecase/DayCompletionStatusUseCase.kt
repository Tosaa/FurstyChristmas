package com.example.furstychristmas.domain.day.usecase

import com.example.furstychristmas.domain.day.model.DayCompleted
import com.example.furstychristmas.domain.day.repository.DayCompletionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate

class DayCompletionStatusUseCase(private val dayCompletionRepository: DayCompletionRepository) {

    val isDatabaseSetup = dayCompletionRepository.isDatabaseSetupForThisYear

    val getDaysToComplete = dayCompletionRepository.allDaysToComplete

    suspend fun isDayDone(date: LocalDate): Boolean = withContext(Dispatchers.IO) {
        val isDone = dayCompletionRepository.getDay(date)?.isDone == true
        Timber.i("isDayDone:$date = $isDone")
        return@withContext isDone
    }

    suspend fun markDayAsDone(date: LocalDate) = withContext(Dispatchers.IO) {
        dayCompletionRepository.updateDay(DayCompleted(date, true))
    }

}