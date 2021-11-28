package redtoss.example.furstychristmas.domain.workout.model

data class Exercise(
    val exerciseId: String,
    val exerciseName: String,
    val muscles: List<Muscle>,
    val startPosition: String,
    val execution: String
)
