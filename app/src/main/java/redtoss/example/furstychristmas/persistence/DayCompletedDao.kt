package redtoss.example.furstychristmas.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate
import redtoss.example.furstychristmas.domain.day.model.Day

@Dao
interface DayCompletedDao {

    @Query("Select count(*) from day")
    fun tableSize(): Int

    @Query("Select * from day")
    fun getDays(): List<Day>

    @Query("Select * from day")
    fun getDaysLD(): LiveData<List<Day>>

    @Query("SELECT * FROM day WHERE day=:date")
    suspend fun getCard(date: LocalDate): Day

    @Query("SELECT * FROM day WHERE day=:date")
    fun getCardLD(date: LocalDate): LiveData<Day>

    @Insert
    suspend fun insertDays(days: List<Day>)

    @Update
    suspend fun updateCard(day: Day)

    @Delete
    suspend fun deleteCard(day: Day)
}
