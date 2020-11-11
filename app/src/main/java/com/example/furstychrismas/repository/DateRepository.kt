package com.example.furstychrismas.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import java.time.LocalDate
import java.time.Month

class DateRepository {

    private val mutableToday = liveData<LocalDate> { emit(LocalDate.now()) } as MutableLiveData
    val today = mutableToday

    val todayAsDayInDecember = today.map {
        if (it.month == Month.DECEMBER) {
            it.dayOfMonth
        } else {
            1
        }
    }

    fun updateDay(newToday: LocalDate) {
        // mutableToday.postValue(newToday)
    }

}