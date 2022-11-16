package redtoss.example.furstychristmas.domain.day.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.persistence.DayDatabase
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class DayCompletionRepository(db: DayDatabase) {

    private val dayCompletedDao = db.dayCompletedDao()

    val allDays = dayCompletedDao.getDaysLD()

    fun getDaysToComplete(year: Int): LiveData<List<Day>> = CompletionLiveData(year, dayCompletedDao.getDaysLD())
    fun isDataBaseSetup(year: Int): Boolean {
        return runBlocking(Dispatchers.IO) {
            dayCompletedDao.getDays().any { it.day.year == year }
        }
    }

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


    class CompletionLiveData(year: LiveData<Int>, allDays: LiveData<List<Day>>) : MediatorLiveData<List<Day>>() {
        constructor(year: Int, allDays: LiveData<List<Day>>) : this(liveData { emit(year) }, allDays)

        private var year = year.value ?: LocalDate.now()
        private var latestDays = allDays.value ?: emptyList()

        init {
            addSource(year.distinctUntilChanged()) { newYear ->
                Timber.d("year value updated: $newYear")
                this.year = newYear
                value = latestDays.filter { it.day.year == newYear }
                Timber.d("allDaysToComplete value: $value")
            }
            addSource(allDays.distinctUntilChanged()) { days ->
                Timber.d("day completion information updated: $days")
                latestDays = days
                value = days.filter { it.day.year == this.year }
                Timber.d("allDaysToComplete value: $value")
            }
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

}
