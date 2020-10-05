package com.example.furstychrismas.screen.day

import androidx.lifecycle.ViewModel
import com.example.furstychrismas.model.Muscle
import com.example.furstychrismas.repository.DayRepository

class ExerciseViewModel(number: Int, dayRepository: DayRepository) : ViewModel() {


    private val workout = dayRepository.getWorkoutOfDay(number + 1)

    val day = "DAY ${workout.day}"

    val reps = workout.workoutRepetition.toString()

    val exercises = workout.drills

    val muscleGroups: Set<Muscle> = exercises.flatMap { it.exercise.muscles }.toSet()


}