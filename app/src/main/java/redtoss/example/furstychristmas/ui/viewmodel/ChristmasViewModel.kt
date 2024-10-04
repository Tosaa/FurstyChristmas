package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.workout.Execution
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.season

@OptIn(ExperimentalCoroutinesApi::class)
class ChristmasViewModel(dayCompletionStatusUseCase: DayCompletionStatusUseCase, workoutUseCase: LoadWorkoutUseCase) : ViewModel() {
    private val completedDays = dayCompletionStatusUseCase.getDaysToCompleteForSeason(DateUtil.today().season()).asFlow().map { list -> list.filter { it.isDone } }
    private val completedWorkouts = completedDays.map { days -> days.mapNotNull { day -> workoutUseCase.getWorkoutAtDay(day.day) } }
    val completedExercises = completedWorkouts.mapLatest { workouts ->
        val exercises = mutableMapOf<AppContent.Exercise, Execution>()
        workouts.forEach { workout ->
            workout.drills.filter { !it.exercise.isPause }.map { drill ->
                repeat(workout.rounds) { exercises.merge(drill.exercise, drill.repetition, Execution::withOther) }
            }
        }
        return@mapLatest exercises
    }

}
