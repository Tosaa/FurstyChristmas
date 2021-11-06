package com.example.furstychristmas.domain.workout.repository

import com.example.furstychristmas.domain.workout.util.ExerciseJsonParser

class ExerciseRepository(private val exerciseParser: ExerciseJsonParser) {

    suspend fun getExercise(exerciseId: String) = exerciseParser.loadAllExercises().find { it.exerciseId == exerciseId }
    suspend fun getAllExercises() = exerciseParser.loadAllExercises()
}