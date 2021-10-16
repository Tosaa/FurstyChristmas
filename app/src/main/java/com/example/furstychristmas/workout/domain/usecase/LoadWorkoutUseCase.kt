package com.example.furstychristmas.workout.domain.usecase

import com.example.furstychristmas.workout.domain.repository.WorkoutRepository
import java.time.LocalDate

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {

    fun getWorkoutAtDay(localDate: LocalDate) = workoutRepository.getContent().firstOrNull { it.date == localDate }
}