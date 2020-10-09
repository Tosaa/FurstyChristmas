package com.example.furstychrismas.screen.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.furstychrismas.model.Card
import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.util.Util
import java.time.LocalDate

class CardViewModel(private val dayRepository: DayRepository) : ViewModel() {
    val date = MutableLiveData(5)

    val today = date.map { Util.intToDayInDecember(it) }

    private val allcards = dayRepository.cardsld

    /*
        val cards = allcards.map { cards ->
            cards.map { card ->
                card.isAvailable = card.day.isBefore(today.value)
                card
            }
        }
    */
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
            card.isAvailable =
                card.day.dayOfYear <= date.dayOfYear
            card
        }
    }

}