package com.example.furstychrismas.screen.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.furstychrismas.repository.CardRepository
import com.example.furstychrismas.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(
    number: Int,
    private val cardRepository: CardRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val dayInDecember = number + 1

    private val workout = workoutRepository.getWorkoutOfDay(dayInDecember)
    private val cardOfWorkout = cardRepository.getCard(dayInDecember)

    val isDone = cardOfWorkout.map { it.isDone }
    val day = workout.map { "DAY ${it.day}" }

    val reps = workout.map { it.workoutRepetition.toString() }

    val exercises = workout.map { it.drills }

    val muscleGroups = exercises.map { it.flatMap { it.exercise.muscles }.toSet() }

    fun markAsDone() {
        viewModelScope.launch {
            cardRepository.markDayAsDone(dayInDecember)
        }
    }

}