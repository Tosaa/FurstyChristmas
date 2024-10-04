package redtoss.example.furstychristmas.calendar.content.parser

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.AppContent

class KExerciseJsonParser : JsonParserInterface<AppContent.Exercise> {

    @Serializable
    private data class PlainExercise(
        val id: String,
        val name: String,
        val start_position: String,
        val execution: String,
        val muscles: List<String>
    )


    override suspend fun parseList(content: String): List<AppContent.Exercise> {
        return Json.decodeFromString<List<PlainExercise>>(content).map { plain ->
            AppContent.Exercise(
                exerciseId = plain.id,
                exerciseName = plain.name,
                muscles = emptyList(), // plain.muscles.map { Muscle.byName(it) },
                startPosition = plain.start_position,
                execution = plain.execution
            )
        }
    }

    override suspend fun parse(content: String): AppContent.Exercise? {
        // t.b.d.
        return null
    }
}