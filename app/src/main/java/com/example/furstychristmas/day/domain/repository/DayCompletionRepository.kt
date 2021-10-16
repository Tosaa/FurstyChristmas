package com.example.furstychristmas.day.domain.repository

import androidx.lifecycle.map
import com.example.furstychristmas.day.domain.model.DayCompleted
import com.example.furstychristmas.persistence.DayDatabase
import com.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class DayCompletionRepository(db: DayDatabase) {

    private val dayCompletedDao = db.dayCompletedDao()

    val allDaysToComplete = dayCompletedDao.getDaysLD().map { it.filter { isDateActive(it.day) } }

    val getCompletedDays = dayCompletedDao.getDaysLD().map { it.filter { it.isDone } }

    val isDatabaseSetupForThisYear: Boolean = dayCompletedDao.getDays().any { isDateActive(it.day) }

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
     * todo: doc
     */
    private fun isDateActive(date: LocalDate): Boolean {
        val isJanuar = DateUtil.today().month == Month.JANUARY
        var thisYear = DateUtil.today().year
        if (isJanuar) thisYear = -1
        return date.year == thisYear
    }

}