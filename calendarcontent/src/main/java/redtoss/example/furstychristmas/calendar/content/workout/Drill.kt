package redtoss.example.furstychristmas.calendar.content.workout


class Drill(val repetition: Execution, val exercise: Exercise) {

    override fun toString(): String {
        return "Drill(repetition=${repetition}, exercise=$exercise)"
    }
}