package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase

class WorkoutViewModel(val workoutUseCase: LoadWorkoutUseCase, val dayCompletionUseCase: DayCompletionStatusUseCase) : ViewModel() {

    var date: LocalDate = LocalDate.now()
    private val workout = flow {
        emit(workoutUseCase.getWorkoutAtDay(date))
    }

    fun completeDay() {
        viewModelScope.launch { dayCompletionUseCase.markDayAsDone(date) }
    }

    var isDone = flow { emit(dayCompletionUseCase.isDayDone(date)) }
    val day = workout.map { it?.date?.dayOfMonth.toString() }
    val exercises = workout.map { it?.drills ?: emptyList() }
    val motto = workout.map { it?.motto ?: "" }
    val rounds = workout.map { it?.rounds ?: 0 }
}