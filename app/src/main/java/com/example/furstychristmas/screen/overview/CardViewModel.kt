package com.example.furstychristmas.screen.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.furstychristmas.model.Card
import com.example.furstychristmas.repository.CardRepository
import com.example.furstychristmas.repository.DateRepository
import java.time.LocalDate

class CardViewModel(
    cardRepository: CardRepository,
    dateRepository: DateRepository
) : ViewModel() {
    val today = dateRepository.today

    private val allcards = cardRepository.cards

    val cards = MediatorLiveData<List<Card>>().apply {
        addSource(allcards) {
            val date = today.value
            if (date != null)
                value = updateCards(it, date)
        }
        addSource(today) {
            val cards = allcards.value
            if (!cards.isNullOrEmpty()) {
                value = updateCards(cards, it)
            }
        }
    }

    private fun updateCards(cards: List<Card>, date: LocalDate): List<Card> {
        return cards.map { card ->
            // card.isAvailable = card.day.dayOfYear <= date.dayOfYear
            card.isAvailable = card.day.dayOfMonth <= date.dayOfMonth
            card
        }
    }

}