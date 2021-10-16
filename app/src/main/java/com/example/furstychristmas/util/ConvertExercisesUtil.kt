package com.example.furstychristmas.util

import android.content.res.Resources
import com.example.furstychristmas.model.Exercise

class ConvertExercisesUtil(private val resources: Resources) {

    fun formatExercise(exercise: Exercise): String {
        return StringBuilder()
            .append("{")
            .append("\"id\":").append("\"").append(exercise.name).append("\"").append(",\n\r")
            .append("\"name\":").append("\"").append(exercise.exerciseName).append("\"").append(",\n\r")
            .append("\"start_position\":").append("\"").append(resources.getString(exercise.startPositionId).replace("\"", "\\\"").replace("\n", "")).append("\"").append(",\n\r")
            .append("\"execution\":").append("\"").append(resources.getString(exercise.repetitionId).replace("\"", "\\\"").replace("\n", "")).append("\"").append(",\n\r")
            .append("\"muscles\":").append(exercise.muscles.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"${it.muscle}\"" }).append("\n\r")
            .append("}").toString()
    }
}