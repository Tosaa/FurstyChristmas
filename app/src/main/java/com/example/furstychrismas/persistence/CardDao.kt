package com.example.furstychrismas.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.furstychrismas.model.Card

@Dao
interface CardDao {

    @Query("Select count(*) from card")
    fun tableSize(): Int

    @Query("Select * from card")
    fun getCards(): LiveData<List<Card>>

    @Insert
    suspend fun insertCard(card: Card)

    @Insert
    suspend fun insertCards(cards: List<Card>)

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)
}