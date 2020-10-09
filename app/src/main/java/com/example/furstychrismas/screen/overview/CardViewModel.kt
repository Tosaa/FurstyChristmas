package com.example.furstychrismas.screen.overview

import androidx.lifecycle.*
import com.example.furstychrismas.model.Card
import com.example.furstychrismas.persistence.CardDao
import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.util.Util
import kotlinx.coroutines.launch
import java.time.LocalDate

class CardViewModel(private val dayRepository: DayRepository) : ViewModel() {
    val date = MutableLiveData(5)

    val today = date.map { Util.intToDayInDecember(it) }

    private val allcards = dayRepository.cardsld

    val cards = allcards.map { cards ->
        cards.map { card ->
            card.isAvailable = card.day.isBefore(today.value)
            card
        }
    }

}