package redtoss.example.furstychristmas.domain.workout.repository

import android.content.res.AssetManager
import java.time.LocalDate
import redtoss.example.furstychristmas.calendar.content.workout.Exercise
import redtoss.example.furstychristmas.calendar.content.workout.WorkoutContent
import redtoss.example.furstychristmas.calendar.content.parser.KExerciseJsonParser
import redtoss.example.furstychristmas.calendar.content.parser.KWorkoutJsonParser
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber

class WorkoutRepository(
    private val exerciseJsonParser: KExerciseJsonParser,
    private val workoutJsonParser: KWorkoutJsonParser,
    private val assetManager: AssetManager
) {
    private var workouts: List<WorkoutContent> = emptyList()

    suspend fun getContent(): List<WorkoutContent> {
        if (workouts.isEmpty()) {
            val fetchedWorkouts = mutableListOf<KWorkoutJsonParser.WorkoutPlain>()
            val exercises = mutableListOf<Exercise>()
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

    private suspend fun createContent(workouts: List<KWorkoutJsonParser.WorkoutPlain>, exercises: List<Exercise>): List<WorkoutContent> {
        return workouts.map { it.toWorkoutContent(exercises) }
    }

    private fun KWorkoutJsonParser.WorkoutPlain.toWorkoutContent(exercises: List<Exercise>): WorkoutContent {
        return WorkoutContent(
            date = LocalDate.parse(this.date),
            drills = this.drills.map { it.toDrill(exercises) },
            rounds = this.rounds,
            bodyparts = this.bodyparts,
            motto = this.motto,
            durationInMinutes = this.durationInMinutes,
        )
    }
}
