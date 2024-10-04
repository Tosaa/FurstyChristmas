package redtoss.example.furstychristmas.calendar.content.parser

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.workout.Drill
import redtoss.example.furstychristmas.calendar.content.workout.Exercise
import redtoss.example.furstychristmas.calendar.content.workout.Repetition
import redtoss.example.furstychristmas.calendar.content.workout.Seconds

class KWorkoutJsonParser : JsonParserInterface {

    @Serializable
    data class Content(
        val type: String,
        val workout: WorkoutPlain
    )

    @Serializable
    data class WorkoutPlain(
        val date: String,
        val drills: List<DrillPlain>,
        val rounds: Int,
        val bodyparts: List<String>,
        val motto: String = "",
        val duration: Int,
        val durationInMinutes: Int = duration,
    ) : AppContent

    @Serializable
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

    override suspend fun parse(content: String): WorkoutPlain? {
        // t.b.d.
        return null
    }

    override suspend fun parseList(content: String): List<WorkoutPlain> {
        return try {
            Json.decodeFromString<List<Content>>(content).map {
                it.workout
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<WorkoutPlain>()
        }
    }
}
