package redtoss.example.furstychristmas.domain.workout.util

import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.info.util.AppContent
import redtoss.example.furstychristmas.domain.jsonparser.JsonParserInterface
import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.model.Repetition
import redtoss.example.furstychristmas.model.Seconds
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

private data class Content(
    val type: String,
    val workout: WorkoutPlain
)

data class WorkoutPlain(
    val date: String,
    val drills: List<DrillPlain>,
    val rounds: Int,
    val bodyparts: List<String>,
    val motto: String = "",
    val duration: Int,
    val durationInMinutes: Int = duration,
) : AppContent

data class DrillPlain(
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

class WorkoutJsonParser(private val assetManager: AssetManager) : JsonParserInterface {


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(List::class.java, Content::class.java)
    private val adapter = moshi.adapter<List<Content>>(type)

    private var loadedWorkouts = emptyList<WorkoutPlain>()


    private suspend fun loadWorkoutOf(year: String): List<WorkoutPlain> {
        Timber.v("loadWorkoutOf(): load workouts for $year")
        return withContext(Dispatchers.IO) {
            loadContent(year, assetManager)
        }
    }

    /*
    private suspend fun loadWorkoutOf(year: String, exercises: List<Exercise>): List<WorkoutContent> {
        Timber.v("loadWorkoutOf(): load workouts for $year and ${exercises.size} exercises")
        val workoutInCurrentYear = loadedWorkouts.firstOrNull { it.date.year == year.toInt() }
        if (workoutInCurrentYear == null) {
            Timber.v("loadWorkoutOf(): Add content for year=${year.toInt()}")
            val newWorkouts = withContext(Dispatchers.IO) {
                val contentPlain = loadContent(year, assetManager)
                Timber.v("load plain workouts: ${contentPlain.size}")
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
            loadedWorkouts = loadedWorkouts.plus(newWorkouts)
        } else {
            Timber.v("loadWorkoutOf(): Content for year=${year.toInt()} exists already, e.g. $workoutInCurrentYear")
        }
        return loadedWorkouts
    }
     */

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
                val workouts = adapter.fromJson(json) ?: emptyList()
                return@withContext workouts.map { it.workout }
            } catch (exception: Exception) {
                Timber.e(exception.fillInStackTrace())
                return@withContext emptyList<WorkoutPlain>()
            }
        } ?: emptyList()
    }

    private suspend fun fetchContent(): List<WorkoutPlain> {
        val loadedContent = loadWorkoutOf("2021")
            .plus(loadWorkoutOf("2022"))
        loadedWorkouts = loadedContent
        return loadedContent
    }

    override suspend fun getContent(): List<WorkoutPlain> {
        return loadedWorkouts.ifEmpty {
            fetchContent()
        }
    }
}
