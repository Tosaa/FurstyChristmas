package redtoss.example.furstychristmas.domain.workout.usecase

import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import timber.log.Timber
import java.time.LocalDate

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {
    private var loadedWorkouts: MutableList<WorkoutContent> = mutableListOf()

    private fun retrieveWorkout(date: LocalDate, workouts: List<WorkoutContent>): WorkoutContent? {
        val workout = workouts.firstOrNull { it.date.sameDayAs(date) }
        if (workout == null) {
            Timber.w("getWorkoutAtDay(): cannot find Workout for $date in ${workouts.joinToString { it.date.toString() }}")
        }
        Timber.v("getWorkoutAtDay(): $date = $workout")
        return workout
    }

    suspend fun getWorkoutAtDay(localDate: LocalDate): WorkoutContent? {
        return if (loadedWorkouts.isEmpty()) {
            workoutRepository.getContent().let {
                loadedWorkouts.addAll(it)
                retrieveWorkout(localDate, it)
            }
        } else {
            retrieveWorkout(localDate, loadedWorkouts)
        }
    }
}
