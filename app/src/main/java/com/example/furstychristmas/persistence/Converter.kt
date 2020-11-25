package com.example.furstychristmas.persistence

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.time.LocalDate

class Converter {

    @TypeConverter
    fun fromTimestamp(value: String): LocalDate? {
        return value.split(".").map { it.toInt() }.let {
            LocalDate.of(it[0], it[1], it[2])
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String {
        return StringBuilder()
            .append(date?.year)
            .append(".")
            .append(date?.monthValue)
            .append(".")
            .append(date?.dayOfMonth).toString()
    }
}