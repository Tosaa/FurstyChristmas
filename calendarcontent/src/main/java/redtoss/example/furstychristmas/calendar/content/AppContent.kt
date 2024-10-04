package redtoss.example.furstychristmas.calendar.content

import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import redtoss.example.furstychristmas.calendar.content.info.InfoPageContent
import redtoss.example.furstychristmas.calendar.content.util.LocalDateSerializer
import redtoss.example.furstychristmas.calendar.content.workout.Drill
import redtoss.example.furstychristmas.calendar.content.workout.RawDrill
import redtoss.example.furstychristmas.calendar.content.workout.Muscle

@Serializable
sealed interface AppContent {

    @Serializable
    data class Info(
        @Serializable(LocalDateSerializer::class)
        val date: LocalDate,
        val title: String,
        val pages: List<InfoPageContent>
    ) : AppContent

    /**
     * Used to combine jsons of [RawWorkout] and [RawDrill]
     */
    @Serializable
    data class RawWorkout(
        @Serializable(LocalDateSerializer::class)
        val date: LocalDate,
        val drills: List<RawDrill>,
        val rounds: Int,
        val bodyparts: List<String>,
        val motto: String = "",
        @SerialName("duration")
        val durationInMinutes: Int
    ) : AppContent

    @Serializable
    data class Workout(
        @Serializable(LocalDateSerializer::class)
        val date: LocalDate,
        val drills: List<Drill>,
        val rounds: Int,
        val bodyparts: List<String>,
        val motto: String = "",
        val durationInMinutes: Int
    ) : AppContent

    /**
     * [Exercise] is mapped by its exerciseId often
     */
    @Serializable
    data class Exercise(
        val exerciseId: String,
        val exerciseName: String,
        val muscles: List<Muscle>,
        val startPosition: String,
        val execution: String
    ) : AppContent {
        val isPause: Boolean = exerciseName.lowercase() == "pause"
    }
}
