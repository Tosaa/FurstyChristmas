package com.example.furstychristmas.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class DateRepository {

    private val mutableToday = liveData<LocalDate> { emit(todayMapped()) } as MutableLiveData
    val today = mutableToday as LiveData<LocalDate>

    /*
    * map days in Nov. to 1.Dez,
    * map days after 25 Dez. to 25 Dez
    * map days in Jan(2021) to 25.Dez
    * map days in Feb(2021) to 31. Nov
    *
    * */
    fun todayMapped(): LocalDate {
        val today = LocalDate.now()
        val dayAfterChristmas = LocalDate.of(2020, 12, 24).plusDays(1)
        val dayBeforeCalendar = LocalDate.of(2020, 12, 1).minusDays(1)
        val fakeDate = when {
            today.isBefore(dayAfterChristmas) && today.isAfter(dayBeforeCalendar) -> today
            today.month == Month.NOVEMBER -> dayBeforeCalendar.plusDays(1)
            today.month == Month.JANUARY && today.year == 2021 -> dayAfterChristmas
            today.year == 2021 -> dayBeforeCalendar
            else -> today
        }
        return fakeDate
    }

    fun updateTime() {
        Timber.d("update time")
        mutableToday.postValue(todayMapped())
    }

}