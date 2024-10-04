package redtoss.example.furstychristmas.domain.workout.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.parser.KExerciseJsonParser
import redtoss.example.furstychristmas.calendar.content.parser.KWorkoutJsonParser
import redtoss.example.furstychristmas.calendar.content.workout.Drill
import redtoss.example.furstychristmas.calendar.content.workout.RawDrill
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber

class WorkoutRepository(
    private val exerciseJsonParser: KExerciseJsonParser,
    private val workoutJsonParser: KWorkoutJsonParser,
    private val assetManager: AssetManager
) {
    private var workouts: List<AppContent.Workout> = emptyList()

    suspend fun getContent(): List<AppContent.Workout> {
        if (workouts.isEmpty()) {
            val fetchedWorkouts = mutableListOf<AppContent.RawWorkout>()
            val exercises = mutableListOf<AppContent.Exercise>()
            exercises.addAll(exerciseJsonParser.parseList(assetManager.readJson("exercise_description.json")))
            (2021..(DateUtil.today().year + 1)).forEach { year ->
                val filename = "calendar${year}_workout.json"
                val foundJson = try {
                    assetManager.readJson(filename)
                } catch (e: Exception) {
                    Timber.d("getContent(): cannot find $filename")
                    return@forEach
                }
                fetchedWorkouts.addAll(workoutJsonParser.parseList(foundJson))
            }
            workouts = createContent(fetchedWorkouts, exercises)
        }
        return workouts
    }

    private suspend fun createContent(workouts: List<AppContent.RawWorkout>, exercises: List<AppContent.Exercise>): List<AppContent.Workout> {
        return workouts.map { it.toWorkoutContent(exercises) }
    }

    private fun AppContent.RawWorkout.toWorkoutContent(exercises: List<AppContent.Exercise>): AppContent.Workout {
        return AppContent.Workout(
            date = this.date,
            drills = this.drills.mapNotNull { it.toDrill(exercises) },
            rounds = this.rounds,
            bodyparts = this.bodyparts,
            motto = this.motto,
            durationInMinutes = this.durationInMinutes,
        )
    }

    private fun RawDrill.toDrill(exercises: List<AppContent.Exercise>): Drill? {
        return Drill(
            repetition = this.repetition,
            exercise = exercises.firstOrNull { this.exerciseId == it.exerciseId } ?: return null
        )
    }
}
