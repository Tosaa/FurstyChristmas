package com.example.furstychrismas

import androidx.lifecycle.ViewModel
import com.example.furstychrismas.model.*

class ExerciseViewModel(number: Int) : ViewModel() {

    val day = "Day ${number + 1}"

    val reps = (number % 5 + 1).toString()

    val exercises = listOf(
        Drill(Seconds(number), Excercise.PUSHUP, Seconds(5)),
        Drill(Repetition(number), Excercise.PUSHUP, Seconds(number * 2)),
        if (number % 3 == 0) Drill(Repetition(20), Excercise.SQUAD, Seconds(5)) else null
    ).filterNotNull()

    val muscleGroups: Set<Muscle> = exercises.flatMap { it.exercise.muscles }.toSet()


}