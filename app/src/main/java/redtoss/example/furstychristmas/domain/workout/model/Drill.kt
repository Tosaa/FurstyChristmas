package redtoss.example.furstychristmas.domain.workout.model

import redtoss.example.furstychristmas.model.Execution

class Drill(val repetition: Execution, val exercise: Exercise) {

    override fun toString(): String {
        return "Drill(repetition=${repetition.formattedString()}, exercise=$exercise)"
    }
}