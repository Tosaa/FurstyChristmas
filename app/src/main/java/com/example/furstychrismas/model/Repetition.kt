package com.example.furstychrismas.model

open class Repetition(private val amount: Int) {
    open fun formatedString(): String {
        return "$amount reps"
    }
}

class Seconds(private val amount: Int) : Repetition(amount) {
    override fun formatedString() : String{
        return "$amount seconds"
    }
}


