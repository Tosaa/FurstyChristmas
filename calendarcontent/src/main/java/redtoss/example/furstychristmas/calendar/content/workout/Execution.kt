package redtoss.example.furstychristmas.calendar.content.workout

abstract class Execution(val amount: Int) {

    fun formattedAmount(): String = amount.toString()

    abstract fun withOther(execution: Execution): Execution

    abstract fun unit(): String
}

class Repetition(amount: Int) : Execution(amount) {
    override fun withOther(execution: Execution): Execution {
        return Repetition(execution.amount + amount)
    }

    override fun unit(): String = " Wdh"
}

class RepetitionPerSide(amount: Int) : Execution(amount) {
    override fun withOther(execution: Execution): Execution {
        return RepetitionPerSide(execution.amount + amount)
    }

    override fun unit(): String = " Wdh/Seite"
}

class Seconds(amount: Int) : Execution(amount) {
    override fun withOther(execution: Execution): Execution {
        return Seconds(execution.amount + amount)
    }

    override fun unit(): String = " Sek"
}

class SecondsPerSide(amount: Int) : Execution(amount) {
    override fun withOther(execution: Execution): Execution {
        return SecondsPerSide(execution.amount + amount)
    }

    override fun unit(): String = " Sek/Seite"
}


