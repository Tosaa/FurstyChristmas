package redtoss.example.furstychristmas.domain.workout.util

import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

class ExerciseJsonParser(private val assetManager: AssetManager) {

    private data class PlainExercise(
        val id: String,
        val name: String,
        val start_position: String,
        val execution: String,
        val muscles: List<String>
    )

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // private val exAdapter = moshi.adapter<Ex>(Ex::class.java)

    val type = Types.newParameterizedType(List::class.java, PlainExercise::class.java)
    private val adapter = moshi.adapter<List<PlainExercise>>(type)

    private var loadedExercises = emptyList<Exercise>()

    suspend fun loadAllExercises(): List<Exercise> {
        if (loadedExercises.isEmpty()) {
            loadedExercises = withContext(Dispatchers.IO) {
                val exercisesPlain = loadEx(assetManager)
                Timber.i("load plain exercises: ${exercisesPlain.size}")
                return@withContext exercisesPlain.map { plain ->
                    try {
                        Exercise(
                            exerciseId = plain.id,
                            exerciseName = plain.name,
                            muscles = emptyList(), // plain.muscles.map { Muscle.byName(it) },
                            startPosition = plain.start_position,
                            execution = plain.execution
                        )
                    } catch (exception: java.lang.Exception) {
                        Timber.e(exception.fillInStackTrace())
                        null
                    }
                }.filterNotNull()
            }
        }
        return loadedExercises
    }

    private suspend fun loadEx(assetManager: AssetManager): List<PlainExercise> {
        val path = "exercise_description.json"
        return withContext(Dispatchers.IO) {
            try {
                val json = BufferedReader(
                    InputStreamReader(
                        assetManager.open(path),
                        "UTF-8"
                    )
                ).readText()
                val workouts = adapter.fromJson(json)
                return@withContext workouts
            } catch (exception: Exception) {
                Timber.e(exception.fillInStackTrace())
                return@withContext emptyList<PlainExercise>()
            }
        } ?: emptyList()
    }
}