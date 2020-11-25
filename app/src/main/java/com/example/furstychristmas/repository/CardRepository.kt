package com.example.furstychristmas.repository

import androidx.lifecycle.LiveData
import com.example.furstychristmas.model.Card
import com.example.furstychristmas.persistence.CardDatabase

class CardRepository(db: CardDatabase) {

    private val cardDao = db.cardDao()

    val cards = cardDao.getCards()

    suspend fun markDayAsDone(dayInDecember: Int) {
        val card = cardDao.getCard(dayInDecember)
        card.isDone = true
        cardDao.updateCard(card)
    }

    fun getCard(dayInDecember: Int): LiveData<Card> {
        return cardDao.getCardLD(dayInDecember)
    }

}