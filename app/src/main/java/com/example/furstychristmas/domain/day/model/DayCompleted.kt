package com.example.furstychristmas.domain.day.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class DayCompleted(
    @PrimaryKey
    val day: LocalDate,
    var isDone: Boolean
) {
    @Ignore
    var isAvailable: Boolean = false
}