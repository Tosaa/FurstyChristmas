package com.example.furstychrismas.model

interface Execution {
    fun formattedString(): String
}

class Repetition(private val amount: Int) : Execution {
    override fun formattedString(): String {
        return "$amount Wdh"
    }
}

class Seconds(private val amount: Int) : Execution {
    override fun formattedString(): String {
        return "$amount Sek."
    }
}


