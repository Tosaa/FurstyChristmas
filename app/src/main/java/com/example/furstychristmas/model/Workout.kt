package com.example.furstychristmas.model

import com.example.furstychristmas.domain.workout.model.Drill

data class Workout(
    val day: Int,
    val drills: List<Drill>,
    val workoutRepetition: Int,
    val motto: String = "",
    val time: Int
) {

    fun getExecutionPerDrill(): Map<Drill, Execution> {
        return drills.map {
            val reps = it.repetition.amount * workoutRepetition
            val repetition = when {
                it.repetition is Repetition -> Repetition(reps)
                it.repetition is RepetitionPerSide -> RepetitionPerSide(reps)
                it.repetition is Seconds -> Seconds(reps)
                it.repetition is SecondsPerSide -> SecondsPerSide(reps)
                else -> Repetition(reps)
            }
            Pair(it, repetition)
        }.toMap()
    }
}
