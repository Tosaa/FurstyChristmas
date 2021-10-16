package com.example.furstychristmas.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.furstychristmas.day.domain.model.DayCompleted

@Database(entities = [DayCompleted::class], exportSchema = false, version = 2)
@TypeConverters(Converter::class)
abstract class DayDatabase : RoomDatabase() {
    abstract fun dayCompletedDao(): DayCompletedDao
}