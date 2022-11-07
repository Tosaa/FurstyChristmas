package redtoss.example.furstychristmas.domain.workout.repository

import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.util.Exercise2020JsonParser
import redtoss.example.furstychristmas.domain.workout.util.ExerciseJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutPlain
import redtoss.example.furstychristmas.model.Repetition
import redtoss.example.furstychristmas.model.Workout
import redtoss.example.furstychristmas.util.Util
import android.content.res.AssetManager
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WorkoutRepository(
    private val parser2020: Exercise2020JsonParser,
    private val exerciseJsonParser: ExerciseJsonParser,
    private val workoutJsonParser: WorkoutJsonParser,
    private val assetManager: AssetManager
) {
    private var workouts : List<WorkoutContent> = emptyList()

    suspend fun getContent(): List<WorkoutContent>{
        if(workouts.isEmpty()) {
            val fetchedWorkouts = mutableListOf<WorkoutPlain>()
            val exercises = mutableListOf<Exercise>()
            exercises.addAll(exerciseJsonParser.parseList(assetManager.readJson("exercise_description.json")))
            listOf("2021","2022").forEach { year ->
                fetchedWorkouts.addAll(workoutJsonParser.parseList(assetManager.readJson("calendar${year}_workout.json")))
            }
            workouts = createContent(fetchedWorkouts, exercises) + parser2020.getContentOf("2020")
        }
        return workouts
    }

    private suspend fun createContent(workouts: List<WorkoutPlain>, exercises: List<Exercise>): List<WorkoutContent> {
        Timber.v("createContent(): ${workouts.size} workouts and ${exercises.size} exercises")
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

    private fun getDummyWorkout() = WorkoutContent(
        date = LocalDate.parse("2021-12-02", DateTimeFormatter.ISO_LOCAL_DATE),
        drills = listOf(Drill(Repetition(42), Exercise("DUMMY", "dummy", listOf(), "start", "end"))),
        rounds = 2,
        bodyparts = listOf("test", "list"),
        motto = "test-motto",
        durationInMinutes = 5
    )

}
