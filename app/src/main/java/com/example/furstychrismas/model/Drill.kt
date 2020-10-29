package com.example.furstychrismas.model

class Drill(val repetition: Repetition, val exercise: Exercise, val breakTime: Repetition) {

    override fun toString(): String {
        return "Drill(repetition=${repetition.formatedString()}, exercise=$exercise, breakTime=${breakTime.formatedString()})"
    }
}