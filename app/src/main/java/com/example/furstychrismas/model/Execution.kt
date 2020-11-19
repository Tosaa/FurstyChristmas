package com.example.furstychrismas.model

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.bold

abstract class Execution(val amount: Int) {

    fun formattedAmount(): String = amount.toString()

    abstract fun unit(): String

    fun formattedString(): SpannableStringBuilder = SpannableStringBuilder("")
        .bold { append(amount.toString())}
        .append(" ${unit()}")
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

class SecondsPerSide(amount: Int) : Execution(amount) {
    override fun unit(): String = " sek/Seite"
}


