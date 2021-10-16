package com.example.furstychristmas.domain.workout.model

import com.example.furstychristmas.model.Execution
import com.example.furstychristmas.model.Exercise

class Drill(val repetition: Execution, val exercise: Exercise, val breakTime: Execution) {

    override fun toString(): String {
        return "Drill(repetition=${repetition.formattedString()}, exercise=$exercise, breakTime=${breakTime.formattedString()})"
    }
}