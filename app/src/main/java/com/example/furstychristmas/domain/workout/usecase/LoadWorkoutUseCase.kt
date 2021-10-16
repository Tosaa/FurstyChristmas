package com.example.furstychristmas.domain.workout.usecase

import com.example.furstychristmas.domain.workout.repository.WorkoutRepository
import java.time.LocalDate

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {

    fun getWorkoutAtDay(localDate: LocalDate) = workoutRepository.getContent().firstOrNull { it.date == localDate }
}