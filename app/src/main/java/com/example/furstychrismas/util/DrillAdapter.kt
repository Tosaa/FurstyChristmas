package com.example.furstychrismas.util

import com.example.furstychrismas.model.Drill
import com.example.furstychrismas.model.Exercise
import com.example.furstychrismas.model.Repetition
import com.example.furstychrismas.model.Seconds
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.lang.StringBuilder

class DrillAdapter {

    private val DELIMITER = ";"

    @ToJson
    fun toJson(drill: Drill): String {
        return StringBuilder()
            .append(drill.repetition.formatedString())
            .append(DELIMITER)
            .append(drill.exercise)
            .append(DELIMITER)
            .append(drill.breakTime.formatedString()).toString()
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

    private fun repetitionFromString(repetition: String): Repetition {
        val amount = repetition.split(' ')[0].toInt()
        return if (repetition.endsWith("reps")) {
            Repetition(amount)
        } else {
            Seconds(amount)
        }
    }


}