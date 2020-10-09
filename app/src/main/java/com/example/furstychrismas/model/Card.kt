package com.example.furstychrismas.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Card(
    @PrimaryKey
    val day: LocalDate,
    var isDone: Boolean
) {
    @Ignore
    var isAvailable: Boolean = false
}