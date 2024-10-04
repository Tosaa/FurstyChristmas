package redtoss.example.furstychristmas.domain.workout.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.calendar.content.workout.Exercise
import redtoss.example.furstychristmas.calendar.content.parser.KExerciseJsonParser
import redtoss.example.furstychristmas.util.readJson

class ExerciseRepository(private val exerciseParser: KExerciseJsonParser, private val assetManager: AssetManager) {
    private var exercises = emptyList<Exercise>()

    suspend fun getContent(): List<Exercise> {
        if (exercises.isEmpty()) {
            exercises = exerciseParser.parseList(assetManager.readJson("exercise_description.json"))
        }
        return exercises
    }

    suspend fun getExercise(exerciseId: String): Exercise? {
        return getContent().find { it.exerciseId == exerciseId }
    }

}
