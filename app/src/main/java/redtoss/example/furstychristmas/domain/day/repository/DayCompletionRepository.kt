package redtoss.example.furstychristmas.domain.day.repository

import androidx.lifecycle.MediatorLiveData
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.persistence.DayDatabase
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class DayCompletionRepository(db: DayDatabase) {

    private val dayCompletedDao = db.dayCompletedDao()

    val allDaysToComplete = MediatorLiveData<List<Day>>().apply {
        var latestToday = DateUtil.today()
        var latestDays = emptyList<Day>()
        addSource(DateUtil.todayAsLiveData) {
            Timber.d("DayCompletionRepository allDaysToComplete 'addSource' date: $it")
            latestToday = it
            value = latestDays.filter { isDateActive(it.day, latestToday) }
        }
        addSource(dayCompletedDao.getDaysLD()) {
            Timber.d("DayCompletionRepository allDaysToComplete 'addSource' days: $it")
            latestDays = it
            value = latestDays.filter { isDateActive(it.day, latestToday) }
        }
    }

    val isDatabaseSetupForThisYear: Boolean = dayCompletedDao.getDays().any { isDateActive(it.day, DateUtil.today()) }

    suspend fun getDay(date: LocalDate): Day? {
        return dayCompletedDao.getDays().firstOrNull { it.day == date }
    }

    suspend fun updateDay(completed: Day) {
        dayCompletedDao.updateCard(completed)
    }

    suspend fun deleteDay(completed: Day) {
        Timber.w("deleteDay is not implemented")
    }

    suspend fun createDays(days: List<Day>) {
        Timber.d("Add days: $days")
        dayCompletedDao.insertDays(days)
    }

    /**
     * The active days are the days where the content for `this year` is visible. This should be from February 1st until last day of January.
     * Background for this idea is that the Calendar is still visible in January, if the new years workouts needs to be done. ;)
     */
    private fun isDateActive(date: LocalDate, today: LocalDate): Boolean {
        return if (today.month == Month.JANUARY) {
            date.year == today.year - 1
        } else {
            date.year == today.year
        }
    }

}