package com.example.furstychrismas.persistence

import androidx.room.*
import com.example.furstychrismas.model.Card

@Database(entities = [Card::class], exportSchema = false, version = 1)
@TypeConverters(Converter::class)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}