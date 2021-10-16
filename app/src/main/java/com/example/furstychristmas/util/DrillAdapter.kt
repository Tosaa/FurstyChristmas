package com.example.furstychristmas.util

import com.example.furstychristmas.domain.workout.model.Drill
import com.example.furstychristmas.model.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class DrillAdapter {

    private val DELIMITER = ";"

    @ToJson
    fun toJson(drill: Drill): String {
        return StringBuilder()
            .append(drill.repetition.formattedString())
            .append(DELIMITER)
            .append(drill.exercise)
            .append(DELIMITER)
            .append(drill.breakTime.formattedString())
            .toString()
    }

    @FromJson
    fun fromJson(string: String): Drill {
        val items = string.split(DELIMITER)
        return Drill(
            repetitionFromString(items[0]),
            Exercise.valueOf(items[1]),
            repetitionFromString(items[2])
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