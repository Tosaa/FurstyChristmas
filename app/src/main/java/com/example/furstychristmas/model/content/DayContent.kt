package com.example.furstychristmas.model.content

import com.example.furstychristmas.model.Drill
import java.time.LocalDate

sealed class DayContent(val date: LocalDate) {
    class Workout(
        date: LocalDate,
        val drills: List<Drill>,
        val rounds: Int,
        val bodyparts: List<String>,
        val motto: String = "",
        val durationInMinutes: Int
    ) : DayContent(date)

    class Info(date: LocalDate, val title: String, val pages: List<InfoPageContent>) : DayContent(date)
}