package com.example.furstychristmas.model

import android.text.SpannableStringBuilder
import androidx.core.text.bold

abstract class Execution(val amount: Int) {

    fun formattedAmount(): String = amount.toString()

    abstract fun withOther(execution: Execution): Execution

    abstract fun unit(): String

    fun formattedString(): SpannableStringBuilder = SpannableStringBuilder("")
        .bold { append(amount.toString()) }
        .append(" ${unit()}")
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

    override fun unit(): String = " sek"
}

class SecondsPerSide(amount: Int) : Execution(amount) {
    override fun withOther(execution: Execution): Execution {
        return SecondsPerSide(execution.amount + amount)
    }

    override fun unit(): String = " sek/Seite"
}


