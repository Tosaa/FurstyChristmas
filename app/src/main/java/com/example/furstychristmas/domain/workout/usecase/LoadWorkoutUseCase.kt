package com.example.furstychristmas.domain.workout.usecase

import com.example.furstychristmas.domain.workout.model.WorkoutContent
import com.example.furstychristmas.domain.workout.repository.WorkoutRepository
import timber.log.Timber
import java.time.LocalDate

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {

    suspend fun getWorkoutAtDay(localDate: LocalDate): WorkoutContent? {
        val workout = workoutRepository.getContent().firstOrNull { it.date == localDate }
        Timber.i("getWorkoutAtDate: $localDate = $workout")
        return workout
    }
}