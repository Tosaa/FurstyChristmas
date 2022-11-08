package redtoss.example.furstychristmas.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import redtoss.example.furstychristmas.domain.day.model.Day
import java.time.LocalDate

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

    /*suspend fun getCard(dayInDecember: Int): Day {
        //return getCard(Util.intToDayInDecember(dayInDecember))
    }

    fun getCardLD(dayInDecember: Int): LiveData<Day> {
        //return getCardLD(Util.intToDayInDecember(dayInDecember))
    }*/

    @Insert
    suspend fun insertDays(days: List<Day>)

    @Update
    suspend fun updateCard(day: Day)

    @Delete
    suspend fun deleteCard(day: Day)
}
