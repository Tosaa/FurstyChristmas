package com.example.furstychristmas.persistence

import androidx.room.*
import com.example.furstychristmas.model.Card

@Database(entities = [Card::class], exportSchema = false, version = 1)
@TypeConverters(Converter::class)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}