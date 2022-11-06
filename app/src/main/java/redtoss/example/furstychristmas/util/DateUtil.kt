package redtoss.example.furstychristmas.util

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import redtoss.example.furstychristmas.BuildConfig
import timber.log.Timber
import java.time.Duration
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.format.DateTimeFormatter

object DateUtil {

    private var dev_day: LocalDate? = null

    fun setDevDay(date: LocalDate) {
        Timber.d("Set DevDate to $date")
        dev_day = date
        CoroutineScope(Dispatchers.Main).launch {
            (todayAsLiveData as TodayLiveData).updateDate(date)
        }
    }

    fun LocalDate.sameDayAs(other: LocalDate): Boolean {
        return other.year == this.year && other.dayOfYear == this.dayOfYear
    }

    fun dayAsIsoDate(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun today(): LocalDate =
        if (BuildConfig.DEBUG)
            dev_day ?: LocalDate.now()
        else
            LocalDate.now()


    /**
     * Returns the validPeriod for the Calendar.
     * This is between the first of November until the last of January
     */
    fun validPeriod(): Period {
        val thisYear = LocalDate.now().year
        val firstDay = LocalDate.of(thisYear, Month.NOVEMBER, 1)
        val lastDay = LocalDate.of(thisYear + 1, Month.FEBRUARY, 1)
        return Period.between(firstDay, lastDay)
    }

    fun isDateInRange(testDate: LocalDate, firstDate: LocalDate, lastDate: LocalDate): Boolean {
        return !(testDate.isBefore(firstDate) || testDate.isAfter(lastDate))
    }

    /**
     * from 1.12.thisyear the notification should pop up to remember to workout
     */
    val firstDayForAlarm = LocalDate.of(LocalDate.now().year, Month.DECEMBER, 1)

    /**
     * until 24.12.thisyear the notification should pop up to remember to workout
     */
    val lastDayForAlarm = LocalDate.of(LocalDate.now().year, Month.DECEMBER, 24)

    val todayAsLiveData = TodayLiveData() as LiveData<LocalDate>

    private class TodayLiveData : LiveData<LocalDate>() {
        private val handler = Handler(Looper.getMainLooper())
        private val updateInterval = Duration.ofMinutes(30).toMillis()

        override fun onActive() {
            super.onActive()

            handler.post { postAndRefreshTimer() }
        }

        fun updateDate(date: LocalDate) {
            value = date
        }

        private fun postAndRefreshTimer() {
            val now = today()
            if (value != now) {
                Timber.d("DateUtil: refresh timer: ${today()}")
                postValue(today())
            }
            handler.postDelayed({ postAndRefreshTimer() }, updateInterval)
        }

        override fun onInactive() {
            super.onInactive()
            handler.removeCallbacksAndMessages(null)
        }
    }
}
