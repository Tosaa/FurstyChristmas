package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import redtoss.example.furstychristmas.domain.workout.usecase.LoadExerciseUseCase

class ExerciseViewModel(exerciseUseCase: LoadExerciseUseCase) : ViewModel() {
    var selectedExercise: String = ""
    private val exercise = flow { emit(exerciseUseCase.loadExerciseById(selectedExercise)) }
    val title = exercise.map { it?.exerciseName ?: "" }
    val description = exercise.map { it?.execution ?: "" }
    val startPosition = exercise.map { it?.startPosition ?: "" }
}