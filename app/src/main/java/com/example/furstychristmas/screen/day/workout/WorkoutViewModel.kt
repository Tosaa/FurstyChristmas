package com.example.furstychristmas.screen.day.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import com.example.furstychristmas.domain.workout.model.WorkoutContent
import com.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class WorkoutViewModel(
    private val date: LocalDate,
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase,
    private val workoutUseCase: LoadWorkoutUseCase
) : ViewModel() {

    private var workout = MutableLiveData<WorkoutContent>()
    var isDone = MutableLiveData(false)

    init {
        viewModelScope.launch {
            workout.postValue(workoutUseCase.getWorkoutAtDay(date))
            isDone.postValue(dayCompletionStatusUseCase.isDayDone(date))
        }
    }

    val workoutstring = workout.map { it?.toString() }

    val cal = date.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.GERMANY)

    val day = workout.map { it?.date?.dayOfMonth.toString() }
    val workoutDuration = workout.map { "${it?.durationInMinutes} min" }

    val reps = workout.map { (it?.rounds ?: 0).toString() }

    val exercises = workout.map { it?.drills ?: emptyList() }

    val muscleGroups = exercises.map { it?.flatMap { it.exercise.muscles }?.toSet() }

    val motto = workout.map { it?.motto ?: "" }

    fun markAsDone() {
        viewModelScope.launch {
            dayCompletionStatusUseCase.markDayAsDone(date)
        }
    }

}