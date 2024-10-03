package redtoss.example.furstychristmas.domain.workout.repository

import android.content.res.AssetManager
import java.time.LocalDate
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.util.Exercise2020JsonParser
import redtoss.example.furstychristmas.domain.workout.util.ExerciseJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutPlain
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber

class WorkoutRepository(
    private val parser2020: Exercise2020JsonParser,
    private val exerciseJsonParser: ExerciseJsonParser,
    private val workoutJsonParser: WorkoutJsonParser,
    private val assetManager: AssetManager
) {
    private var workouts: List<WorkoutContent> = emptyList()

    suspend fun getContent(): List<WorkoutContent> {
        if (workouts.isEmpty()) {
            val fetchedWorkouts = mutableListOf<WorkoutPlain>()
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
            workouts = createContent(fetchedWorkouts, exercises) + parser2020.getContentOf("2020")
        }
        return workouts
    }

    private suspend fun createContent(workouts: List<WorkoutPlain>, exercises: List<Exercise>): List<WorkoutContent> {
        return workouts.map { it.toWorkoutContent(exercises) }
    }

    private fun WorkoutPlain.toWorkoutContent(exercises: List<Exercise>): WorkoutContent {
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
