package redtoss.example.furstychristmas.domain.workout.repository

import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.util.Exercise2020JsonParser
import redtoss.example.furstychristmas.domain.workout.util.ExerciseJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutJsonParser
import redtoss.example.furstychristmas.model.Repetition
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WorkoutRepository(private val parser2020: Exercise2020JsonParser, private val exerciseParser: ExerciseJsonParser, private val workoutJsonParser: WorkoutJsonParser) {

    private fun getDummyWorkout() = WorkoutContent(
        date = LocalDate.parse("2021-12-02", DateTimeFormatter.ISO_LOCAL_DATE),
        drills = listOf(Drill(Repetition(42), Exercise("DUMMY", "dummy", listOf(), "start", "end"))),
        rounds = 2,
        bodyparts = listOf("test", "list"),
        motto = "test-motto",
        durationInMinutes = 5
    )

    suspend fun getContent(): List<WorkoutContent> = getContentOf2020()
        .plus(getContentOf2021())

    private suspend fun getContentOf2021(): List<WorkoutContent> {
        val exercises = exerciseParser.loadAllExercises()
        return workoutJsonParser.loadWorkoutOf("2021", exercises = exercises)
    }

    private suspend fun getContentOf2020(): List<WorkoutContent> {
        return parser2020.getContentOf("2020")
    }


}