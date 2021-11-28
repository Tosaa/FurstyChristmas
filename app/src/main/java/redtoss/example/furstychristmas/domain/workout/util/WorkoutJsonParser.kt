package redtoss.example.furstychristmas.domain.workout.util

import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.model.Repetition
import redtoss.example.furstychristmas.model.Seconds
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WorkoutJsonParser(private val assetManager: AssetManager) {

    private data class DrillPlain(
        val exercise: String,
        val reps: Int = 0,
        val duration: Int = 0
    ) {
        fun toDrill(exercises: List<Exercise>): Drill {
            val exercise = exercises.first { it.exerciseId.equals(exercise, true) }
            return when {
                duration != 0 -> Drill(Seconds(duration), exercise)
                reps != 0 -> Drill(Repetition(reps), exercise)
                else -> Drill(Repetition(reps), exercise)
            }
        }
    }

    private data class WorkoutPlain(
        val date: String,
        val bodyparts: List<String>,
        val rounds: Int,
        val drills: List<DrillPlain>,
        val motto: String,
        val duration: Int
    )

    private data class Content(
        val type: String,
        val workout: WorkoutPlain
    )

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(List::class.java, Content::class.java)
    private val adapter = moshi.adapter<List<Content>>(type)

    private var loadedWorkouts = emptyList<WorkoutContent>()

    suspend fun loadWorkoutOf(year: String, exercises: List<Exercise>): List<WorkoutContent> {
        if (loadedWorkouts.isEmpty()) {
            loadedWorkouts = withContext(Dispatchers.IO) {
                val contentPlain = loadContent(year, assetManager)
                Timber.i("load plain workouts: ${contentPlain.size}")
                return@withContext contentPlain.map { plainWorkout ->
                    try {
                        val drills = plainWorkout.drills.map { it.toDrill(exercises) }
                        val date = LocalDate.parse(plainWorkout.date, DateTimeFormatter.ISO_LOCAL_DATE)
                        WorkoutContent(
                            date = date,
                            drills = drills,
                            rounds = plainWorkout.rounds,
                            bodyparts = drills.flatMap { it.exercise.muscles.map { it.muscle } },
                            motto = plainWorkout.motto,
                            durationInMinutes = plainWorkout.duration
                        )
                    } catch (exception: java.lang.Exception) {
                        Timber.e(exception.fillInStackTrace())
                        null
                    }
                }.filterNotNull()
            }
        }
        return loadedWorkouts
    }

    private suspend fun loadContent(year: String, assetManager: AssetManager): List<WorkoutPlain> {
        val path = "calendar${year}_workout.json"
        return withContext(Dispatchers.IO) {
            try {
                val json = BufferedReader(
                    InputStreamReader(
                        assetManager.open(path),
                        "UTF-8"
                    )
                ).readText()
                val workouts = adapter.fromJson(json)
                return@withContext workouts?.map { it.workout }
            } catch (exception: Exception) {
                Timber.e(exception.fillInStackTrace())
                return@withContext emptyList<WorkoutPlain>()
            }
        } ?: emptyList()
    }
}