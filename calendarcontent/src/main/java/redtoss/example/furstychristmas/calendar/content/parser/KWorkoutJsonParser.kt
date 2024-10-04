package redtoss.example.furstychristmas.calendar.content.parser

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.AppContent

class KWorkoutJsonParser : JsonParserInterface<AppContent.RawWorkout> {

    @Serializable
    data class WorkoutWrapper(val type: String, val workout: AppContent.RawWorkout)

    override suspend fun parse(content: String): AppContent.RawWorkout? {
        // t.b.d.
        return null
    }

    override suspend fun parseList(content: String): List<AppContent.RawWorkout> {
        return try {
            Json.decodeFromString<List<WorkoutWrapper>>(content).map {
                it.workout
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
