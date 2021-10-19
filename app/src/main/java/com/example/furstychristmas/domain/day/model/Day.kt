package com.example.furstychristmas.domain.day.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Day(
    @PrimaryKey
    val day: LocalDate,
    var isDone: Boolean
)