package com.example.furstychristmas.model

class Drill(val repetition: Execution, val exercise: Exercise, val breakTime: Execution) {

    override fun toString(): String {
        return "Drill(repetition=${repetition.formattedString()}, exercise=$exercise, breakTime=${breakTime.formattedString()})"
    }
}