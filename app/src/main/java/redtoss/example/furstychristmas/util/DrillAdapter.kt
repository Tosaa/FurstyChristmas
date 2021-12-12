package redtoss.example.furstychristmas.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.model.*

class DrillAdapter {

    private val DELIMITER = ";"

    @ToJson
    fun toJson(drill: Drill): String {
        return StringBuilder()
            .append(drill.repetition.formattedString())
            .append(DELIMITER)
            .append(drill.exercise)
            .toString()
    }

    @FromJson
    fun fromJson(string: String): Drill {
        val items = string.split(DELIMITER)
        return Drill(
            repetitionFromString(items[0]),
            ExerciseOLD.valueOf(items[1]).toExercise()
        )
    }

    private fun repetitionFromString(repetition: String): Execution {
        val amount = repetition.split(' ')[0].toInt()
        return if (repetition.endsWith("reps")) {
            Repetition(amount)
        } else if (repetition.endsWith("secs")) {
            Seconds(amount)
        } else if (repetition.endsWith("reps/s")) {
            RepetitionPerSide(amount)
        } else if (repetition.endsWith("secs/s")) {
            SecondsPerSide(amount)
        } else {
            Repetition(amount)
        }
    }

}