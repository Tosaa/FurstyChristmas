package redtoss.example.furstychristmas.domain.workout.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.info.util.AppContent
import redtoss.example.furstychristmas.domain.jsonparser.JsonParserInterface
import redtoss.example.furstychristmas.domain.workout.model.Exercise

class ExerciseJsonParser : JsonParserInterface {

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

    val type = Types.newParameterizedType(List::class.java, PlainExercise::class.java)
    private val adapter = moshi.adapter<List<PlainExercise>>(type)

    override suspend fun parseList(content: String): List<Exercise> {
        return withContext(Dispatchers.IO) {
            return@withContext adapter.fromJson(content)?.map { plain ->
                Exercise(
                    exerciseId = plain.id,
                    exerciseName = plain.name,
                    muscles = emptyList(), // plain.muscles.map { Muscle.byName(it) },
                    startPosition = plain.start_position,
                    execution = plain.execution
                )
            } ?: emptyList()
        }
    }

    override suspend fun parse(content: String): AppContent? {
        // t.b.d.
        return null
    }
}
