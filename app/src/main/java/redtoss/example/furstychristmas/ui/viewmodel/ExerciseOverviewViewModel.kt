package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import redtoss.example.furstychristmas.domain.workout.usecase.LoadExerciseUseCase

class ExerciseOverviewViewModel(exerciseUseCase: LoadExerciseUseCase) : ViewModel() {
    val exercises = flow { emit(exerciseUseCase.loadAllExercises().filter { !it.isPause }.sortedBy { it.exerciseName }) }
}
