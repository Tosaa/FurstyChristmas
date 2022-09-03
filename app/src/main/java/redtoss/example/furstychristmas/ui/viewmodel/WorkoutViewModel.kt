package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import java.time.LocalDate

class WorkoutViewModel(val workoutUseCase: LoadWorkoutUseCase) : ViewModel() {
    var date: LocalDate = LocalDate.now()
    private val workout = flow {
        emit(workoutUseCase.getWorkoutAtDay(date))
    }

    var isDone = MutableLiveData(false)
    val day = workout.map { it?.date?.dayOfMonth.toString() }
    val exercises = workout.map { it?.drills ?: emptyList() }
    val motto = workout.map { it?.motto ?: "" }
}