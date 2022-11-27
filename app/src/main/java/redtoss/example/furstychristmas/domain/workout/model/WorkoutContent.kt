package redtoss.example.furstychristmas.domain.workout.model

import redtoss.example.furstychristmas.domain.info.util.AppContent
import java.time.LocalDate

data class WorkoutContent(
    val date: LocalDate,
    val drills: List<Drill>,
    val rounds: Int,
    val bodyparts: List<String>,
    val motto: String = "",
    val durationInMinutes: Int
) : AppContent {
    override fun toString(): String {
        return "$date,${drills.map { it.exercise.exerciseId }},$rounds"
    }
}
