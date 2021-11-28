package redtoss.example.furstychristmas.util

import android.content.res.Resources
import redtoss.example.furstychristmas.model.ExerciseOLD

class ConvertExercisesUtil(private val resources: Resources) {

    fun formatExercise(exerciseOLD: ExerciseOLD): String {
        return StringBuilder()
            .append("{")
            .append("\"id\":").append("\"").append(exerciseOLD.name).append("\"").append(",\n\r")
            .append("\"name\":").append("\"").append(exerciseOLD.exerciseName).append("\"").append(",\n\r")
            .append("\"start_position\":").append("\"").append(resources.getString(exerciseOLD.startPositionId).replace("\"", "\\\"").replace("\n", "")).append("\"").append(",\n\r")
            .append("\"execution\":").append("\"").append(resources.getString(exerciseOLD.repetitionId).replace("\"", "\\\"").replace("\n", "")).append("\"").append(",\n\r")
            .append("\"muscles\":").append(exerciseOLD.muscles.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"${it.muscle}\"" }).append("\n\r")
            .append("}").toString()
    }
}