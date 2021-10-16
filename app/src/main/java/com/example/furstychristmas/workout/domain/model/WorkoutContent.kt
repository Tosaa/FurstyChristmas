package com.example.furstychristmas.workout.domain.model

import java.time.LocalDate

data class WorkoutContent(
    val date: LocalDate,
    val drills: List<Drill>,
    val rounds: Int,
    val bodyparts: List<String>,
    val motto: String = "",
    val durationInMinutes: Int
)