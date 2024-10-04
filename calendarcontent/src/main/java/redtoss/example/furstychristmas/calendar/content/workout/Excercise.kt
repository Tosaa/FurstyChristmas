package redtoss.example.furstychristmas.calendar.content.workout

import redtoss.example.furstychristmas.calendar.content.AppContent

data class Exercise(
    val exerciseId: String,
    val exerciseName: String,
    val muscles: List<Muscle>,
    val startPosition: String,
    val execution: String
) : AppContent {
    val isPause: Boolean = exerciseName.lowercase() == "pause"
}
