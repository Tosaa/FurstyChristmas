package redtoss.example.furstychristmas.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import redtoss.example.furstychristmas.domain.day.model.Day

@Database(entities = [Day::class], exportSchema = false, version = 3)
@TypeConverters(Converter::class)
abstract class DayDatabase : RoomDatabase() {
    abstract fun dayCompletedDao(): DayCompletedDao
}