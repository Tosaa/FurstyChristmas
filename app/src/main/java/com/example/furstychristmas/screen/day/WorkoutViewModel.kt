package com.example.furstychristmas.screen.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.furstychristmas.model.Workout
import com.example.furstychristmas.repository.CardRepository
import com.example.furstychristmas.repository.WorkoutRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class WorkoutViewModel(
    number: Int,
    private val cardRepository: CardRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val dayInDecember = number + 1

    private val workout = liveData<Workout> { emit(workoutRepository.getWorkoutOfDay(dayInDecember)) }
    private val cardOfWorkout = cardRepository.getCard(dayInDecember)

    val isDone = cardOfWorkout.map { it.isDone }
    val cal = LocalDate.of(LocalDate.now().year,12,dayInDecember).dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.GERMANY)
    val day = workout.map { "${it.day}" }
    val workoutDuration = workout.map { "${it.time} min" }

    val reps = workout.map { it.workoutRepetition.toString() }

    val exercises = workout.map { it.drills }

    val muscleGroups = exercises.map { it.flatMap { it.exercise.muscles }.toSet() }

    val motto = workout.map { it.motto }
    fun markAsDone() {
        viewModelScope.launch {
            cardRepository.markDayAsDone(dayInDecember)
        }
    }

}