package com.example.furstychristmas.repository.content

import com.example.furstychristmas.model.content.DayContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ContentUseCase(val infoRepository: InfoRepository, val workoutRepository: WorkoutRepository) {

    private var contents = infoRepository.getContent() + workoutRepository.getContent()

    fun isDayWorkout(date: LocalDate): Boolean = contents.first { it.date == date } is DayContent.Workout

    fun isDayInfo(date: LocalDate): Boolean = contents.first { it.date == date } is DayContent.Info

    suspend fun getContentOfDay(date: LocalDate): DayContent? {
        return withContext(Dispatchers.IO) {
            val infoContent = infoRepository.getContent().firstOrNull { it.date == date }
            val workoutContent = workoutRepository.getContent().firstOrNull { it.date == date }
            infoContent ?: workoutContent
        }
    }
}