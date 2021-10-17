package com.example.furstychristmas.domain.day.repository

import androidx.lifecycle.MediatorLiveData
import com.example.furstychristmas.domain.day.model.DayCompleted
import com.example.furstychristmas.persistence.DayDatabase
import com.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class DayCompletionRepository(db: DayDatabase) {

    private val dayCompletedDao = db.dayCompletedDao()

    // val allDaysToComplete = dayCompletedDao.getDaysLD().map { days -> days.filter { isDateActive(it.day) } }

    val allDaysToComplete = MediatorLiveData<List<DayCompleted>>().apply {
        var latestToday = DateUtil.today()
        var latestDays = emptyList<DayCompleted>()
        addSource(DateUtil.todayAsLiveData) {
            latestToday = it
            value = latestDays.filter { isDateActive(it.day, latestToday) }
        }
        addSource(dayCompletedDao.getDaysLD()) {
            latestDays = it
            value = latestDays.filter { isDateActive(it.day, latestToday) }
        }
    }

    val isDatabaseSetupForThisYear: Boolean = dayCompletedDao.getDays().any { isDateActive(it.day, DateUtil.today()) }

    suspend fun getDay(date: LocalDate): DayCompleted? {
        return dayCompletedDao.getDays().firstOrNull { it.day == date }
    }

    suspend fun updateDay(completed: DayCompleted) {
        dayCompletedDao.updateCard(completed)
    }

    suspend fun deleteDay(completed: DayCompleted) {

    }

    suspend fun createDays(days: List<DayCompleted>) {
        Timber.d("Add days: $days")
        dayCompletedDao.insertDays(days)
    }

    /**
     * The active days are the days where the content for `this year` is visible. This should be from February 1st until last day of January.
     * Background for this idea is that the Calendar is still visible in Januar, if the new years workouts needs to be done. ;)
     */
    private fun isDateActive(date: LocalDate, today: LocalDate): Boolean {
        val isJanuar = today.month == Month.JANUARY
        var thisYear = today.year
        if (isJanuar) thisYear = -1
        return date.year == thisYear
    }

}