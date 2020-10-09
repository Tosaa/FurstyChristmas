package com.example.furstychrismas.util

import com.example.furstychrismas.model.Card
import com.example.furstychrismas.persistence.CardDatabase
import java.time.LocalDate

object Util {
    fun intToDayInDecember(day: Int): LocalDate {
        return LocalDate.of(2020, 12, day)
    }

    suspend fun createDaysInDB(cardDatabase: CardDatabase) {
        cardDatabase.cardDao().insertCards(IntRange(1, 24).map {
            Card(intToDayInDecember(it), false)
        })

    }

}