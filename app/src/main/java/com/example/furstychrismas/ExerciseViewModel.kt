package com.example.furstychrismas

import androidx.lifecycle.ViewModel
import com.example.furstychrismas.model.Drill
import com.example.furstychrismas.model.Excercise
import com.example.furstychrismas.model.Repetition
import com.example.furstychrismas.model.Seconds

class ExerciseViewModel(number: Int) : ViewModel() {

    val day = "Day $number"

    val reps = (number % 5).toString()

    val exercises = listOf(
        Drill(Seconds(number), Excercise.PUSHUP, Seconds(5)),
        Drill(Repetition(number), Excercise.PUSHUP, Seconds(number * 2))
    )


}