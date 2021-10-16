package com.example.furstychristmas.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.furstychristmas.day.domain.model.DayCompleted
import com.example.furstychristmas.util.Util
import java.time.LocalDate

@Dao
interface DayCompletedDao {

    @Query("Select count(*) from daycompleted")
    fun tableSize(): Int

    @Query("Select * from daycompleted")
    fun getDays(): List<DayCompleted>

    @Query("Select * from daycompleted")
    fun getDaysLD(): LiveData<List<DayCompleted>>

    @Query("SELECT * FROM daycompleted WHERE day=:date")
    suspend fun getCard(date: LocalDate): DayCompleted

    @Query("SELECT * FROM daycompleted WHERE day=:date")
    fun getCardLD(date: LocalDate): LiveData<DayCompleted>

    suspend fun getCard(dayInDecember: Int): DayCompleted {
        return getCard(Util.intToDayInDecember(dayInDecember))
    }

    fun getCardLD(dayInDecember: Int): LiveData<DayCompleted> {
        return getCardLD(Util.intToDayInDecember(dayInDecember))
    }


    @Insert
    suspend fun insertDays(days: List<DayCompleted>)

    @Update
    suspend fun updateCard(dayCompleted: DayCompleted)

    @Delete
    suspend fun deleteCard(dayCompleted: DayCompleted)
}