package com.example.furstychrismas.model

abstract class Execution(val amount: Int) {
    fun formattedString(): String {
        return "$amount${unit()}"
    }

    fun formattedAmount():String = amount.toString()

    abstract fun unit(): String
}

class Repetition(amount: Int) : Execution(amount) {
    override fun unit(): String = " Wdh"
}

class RepetitionPerSide(amount: Int) : Execution(amount) {

    override fun unit(): String = " Wdh/Seite"
}

class Seconds(amount: Int) : Execution(amount) {
    override fun unit(): String = " sek"

}


