package com.example.furstychristmas.screen.day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.furstychristmas.model.content.DayContent
import com.example.furstychristmas.repository.content.ContentUseCase
import com.example.furstychristmas.repository.day.DayCompletionStatusUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class WorkoutViewModel(
    private val date: LocalDate,
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase,
    private val contentUseCase: ContentUseCase
) : ViewModel() {

    private var content = MutableLiveData<DayContent>()
    var isDone = MutableLiveData(false)

    init {
        viewModelScope.launch { content.postValue(contentUseCase.getContentOfDay(date)) }
        viewModelScope.launch { isDone.postValue(dayCompletionStatusUseCase.isDayDone(date)) }
    }
/*
    private val workout = content as? DayContent.Workout
    private val info = content as? DayContent.Info

    val workoutstring = workout?.toString() ?: ""
    val infoString = info?.toString() ?: ""

    val cal = date.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.GERMANY)

    val day = date
    val workoutDuration = (workout?.durationInMinutes ?: 0).toString() +" min"

    val reps = (workout?.rounds ?: 0).toString()

    val exercises = emptyList<Drill>()

    val muscleGroups = emptySet<Muscle>()

    val motto = ""
*/
    // workout should be wrapped and not every variable should be exposed individually.
    // workout start

    val workout = content.map { it as? DayContent.Workout }
    val info = content.map { it as? DayContent.Info }

    val workoutstring = workout.map { it.toString() }
    val infoString = info.map { it.toString() }


    val cal = date.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.GERMANY)

    val day = workout.map { "${it?.date}" }
    val workoutDuration = workout.map { "${it?.durationInMinutes} min" }

    val reps = workout.map { (it?.rounds ?: 0).toString() }

    val exercises = workout.map { it?.drills ?: emptyList() }

    val muscleGroups = exercises.map { it?.flatMap { it.exercise.muscles }?.toSet() }

    val motto = workout.map { it?.motto ?: "" }

    // workout end

    fun markAsDone() {
        viewModelScope.launch {
            dayCompletionStatusUseCase.markDayAsDone(date)
        }
    }

}