package redtoss.example.furstychristmas.domain.workout.model

import java.time.LocalDate

data class WorkoutPlain(
    val date: LocalDate,
    val drills: List<Drill>,
    val rounds: Int,
    val bodyparts: List<String>,
    val motto: String = "",
    val durationInMinutes: Int
)