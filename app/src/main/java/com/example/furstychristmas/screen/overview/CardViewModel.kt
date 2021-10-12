package com.example.furstychristmas.screen.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.furstychristmas.model.Card
import com.example.furstychristmas.repository.CardRepository
import com.example.furstychristmas.util.DateUtil
import java.time.LocalDate
import java.time.Month

class CardViewModel(
    cardRepository: CardRepository
) : ViewModel() {
    private val allCards = cardRepository.cards

    val cards = MediatorLiveData<List<Card>>().apply {
        addSource(allCards) {
            value = updateCards(it, DateUtil.today())
        }
        addSource(DateUtil.todayAsLiveData) {
            val cards = allCards.value
            if (!cards.isNullOrEmpty()) {
                value = updateCards(cards, it)
            }
        }
    }

    private fun updateCards(cards: List<Card>, date: LocalDate): List<Card> {
        return cards.map { card ->
            // card.isAvailable = card.day.dayOfYear <= date.dayOfYear
            val development = true
            val itIsJanuary = date.month == Month.JANUARY
            val dateOfCardIsInThePast = card.day.isBefore(date)
            card.isAvailable = itIsJanuary || dateOfCardIsInThePast || development
            card
        }
    }

}