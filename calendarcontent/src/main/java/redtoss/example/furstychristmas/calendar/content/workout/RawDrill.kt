package redtoss.example.furstychristmas.calendar.content.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import redtoss.example.furstychristmas.calendar.content.AppContent

/**
 * Used to convert a single exerciseId of a json into a full described [AppContent.Exercise]
 */
@Serializable
data class RawDrill(
    val reps: Int? = null,
    val duration: Int? = null,
    @SerialName("exercise")
    val exerciseId: String
) {
    val repetition: Execution
        get() = reps?.let { Execution.Repetition(it) } ?: duration?.let { Execution.Seconds(it) } ?: Execution.Repetition(0)
}

@Serializable
data class Drill(val repetition: Execution, val exercise: AppContent.Exercise)