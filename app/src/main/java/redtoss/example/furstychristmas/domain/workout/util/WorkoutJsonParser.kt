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

class WorkoutJsonParser() : JsonParserInterface {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(List::class.java, Content::class.java)
    private val adapter = moshi.adapter<List<Content>>(type)

    public override suspend fun parse(content: String): WorkoutPlain? {
        // t.b.d.
        return null
    }

    public override suspend fun parseList(content: String): List<WorkoutPlain> {
        return withContext(Dispatchers.IO) {
            return@withContext adapter.fromJson(content)?.mapNotNull { it.workout } ?: emptyList()
        }
    }

}
