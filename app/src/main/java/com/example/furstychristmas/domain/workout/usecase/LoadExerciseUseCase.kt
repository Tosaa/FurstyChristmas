package com.example.furstychristmas.domain.workout.usecase

import com.example.furstychristmas.domain.workout.model.Exercise
import com.example.furstychristmas.domain.workout.repository.ExerciseRepository
import timber.log.Timber

class LoadExerciseUseCase(private val exerciseRepository: ExerciseRepository) {

    suspend fun loadExerciseById(exerciseId: String): Exercise? {
        Timber.i("load exercise: $exerciseId")
        val foundExercise = exerciseRepository.getExercise(exerciseId)
        Timber.i("found: $foundExercise")
        return foundExercise
    }

    suspend fun loadAllExercises(): List<Exercise> {
        Timber.i("load all exercises")
        return exerciseRepository.getAllExercises()
    }
}