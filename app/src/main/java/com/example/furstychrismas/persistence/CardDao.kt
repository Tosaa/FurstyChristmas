package com.example.furstychrismas.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.furstychrismas.model.Card
import com.example.furstychrismas.util.Util
import java.time.LocalDate

@Dao
interface CardDao {

    @Query("Select count(*) from card")
    fun tableSize(): Int

    @Query("Select * from card")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE day=:date")
    suspend fun getCard(date: LocalDate): Card

    @Query("SELECT * FROM card WHERE day=:date")
    fun getCardLD(date: LocalDate): LiveData<Card>

    suspend fun getCard(dayInDecember: Int): Card {
        return getCard(Util.intToDayInDecember(dayInDecember))
    }

    fun getCardLD(dayInDecember: Int): LiveData<Card> {
        return getCardLD(Util.intToDayInDecember(dayInDecember))
    }


    @Insert
    suspend fun insertCard(card: Card)

    @Insert
    suspend fun insertCards(cards: List<Card>)

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)
}