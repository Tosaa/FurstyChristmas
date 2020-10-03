package com.example.furstychrismas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.furstychrismas.model.Card

class CardViewModel : ViewModel() {
    val date = MutableLiveData<Int>(0)

    val cards = Transformations.map(date) { currentDate ->
        IntRange(1, 24).map { Card(it, it <= currentDate, false) }
    }

}