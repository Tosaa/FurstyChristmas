package redtoss.example.furstychristmas.domain.day.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.persistence.DayDatabase
import timber.log.Timber

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
                this.year = newYear
                value = latestDays.filter { it.day.year == newYear }
            }
            addSource(allDays.distinctUntilChanged()) { days ->
                latestDays = days
                value = days.filter { it.day.year == this.year }
            }
        }
    }

}
