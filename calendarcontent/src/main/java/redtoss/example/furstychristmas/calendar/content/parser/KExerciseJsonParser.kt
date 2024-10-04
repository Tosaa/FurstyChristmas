package redtoss.example.furstychristmas.calendar.content.parser

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.workout.Exercise

class KExerciseJsonParser : JsonParserInterface {
    @Serializable
    private data class PlainExercise(
        val id: String,
        val name: String,
        val start_position: String,
        val execution: String,
        val muscles: List<String>
    )


    override suspend fun parseList(content: String): List<Exercise> {
        return Json.decodeFromString<List<PlainExercise>>(content).map { plain ->
            Exercise(
                exerciseId = plain.id,
                exerciseName = plain.name,
                muscles = emptyList(), // plain.muscles.map { Muscle.byName(it) },
                startPosition = plain.start_position,
                execution = plain.execution
            )
        }
    }

    override suspend fun parse(content: String): AppContent? {
        // t.b.d.
        return null
    }
}