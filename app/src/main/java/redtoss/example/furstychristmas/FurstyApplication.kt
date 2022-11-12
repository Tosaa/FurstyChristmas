package redtoss.example.furstychristmas

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import redtoss.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.koin.dbModule
import redtoss.example.furstychristmas.koin.myModule
import redtoss.example.furstychristmas.receiver.DailyNotificationReceiver
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.season
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class FurstyApplication : Application() {
    private lateinit var backgroundScope: CoroutineScope
    private val preferences: SharedPreferences by inject()
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase by inject()
    private val addDayCompletionUseCase: AddDayCompletionUseCase by inject()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("onCreate()")
        backgroundScope = CoroutineScope(Job() + Dispatchers.IO)
        startKoin {
            Timber.d("startKoin()")
            androidLogger()
            androidContext(this@FurstyApplication)
            modules(dbModule, myModule)
        }
        backgroundScope.launch {
            preferences.getString("developer_date", null)?.let {
                val date = LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
                Timber.d("Initialise App with developer_date: $date")
                DateUtil.setDevDay(date)
            }
            setupDatabaseIfNecessary()
            activateDayIfNecessary()
        }
    }


    private suspend fun activateDayIfNecessary() {
        val today = DateUtil.today()
        if (DateUtil.isDateInRange(today, DateUtil.firstDayForAlarm, DateUtil.lastDayForAlarm)) {
            Timber.d("set alarm for $today")
            setRecurringAlarm(applicationContext, today)
        }
    }


    private suspend fun setupDatabaseIfNecessary() {
        Timber.d("setupDatabaseIfNecessary()")
        val today = DateUtil.today()
        val activeSeason = today.season()
        if (!dayCompletionStatusUseCase.isDataBaseSetupForSeason(activeSeason)) {
            Timber.d("setupDatabaseIfNecessary(): Add Entries to Database for year (${today.year}) / season ($activeSeason)")
            val days = IntRange(1, 24).map { day -> LocalDate.of(activeSeason, Month.DECEMBER, day) }.toList()
            addDayCompletionUseCase.addDefaultEntriesForDates(days)
        } else {
            Timber.d("setupDatabaseIfNecessary(): Entries for year ${today.year} / season ($activeSeason) exist already")
        }
    }

    private fun setRecurringAlarm(context: Context, dayForAlarm: LocalDate) {
        Timber.d("setRecurringAlarm()")
        var updateTime = dayForAlarm.atTime(17, 30).toInstant(ZoneOffset.ofHours(1)).toEpochMilli()
        val now = System.currentTimeMillis()
        if (now > updateTime) {
            Timber.d("change Day to tomorrow:$now - $updateTime")
            updateTime = dayForAlarm.plusDays(1).atTime(17, 30).toInstant(ZoneOffset.ofHours(1)).toEpochMilli()
        }
        val repeatedNotificationIntent = Intent(context, DailyNotificationReceiver::class.java)
        val pendingNotificationIntent = PendingIntent.getBroadcast(
            context,
            0, repeatedNotificationIntent, PendingIntent.FLAG_CANCEL_CURRENT + PendingIntent.FLAG_IMMUTABLE
        )
        val alarms = this.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager
        alarms.setRepeating(
            AlarmManager.RTC_WAKEUP,
            updateTime,
            AlarmManager.INTERVAL_DAY,
            pendingNotificationIntent
        )
        Timber.d("set Wakeup Alarm for $updateTime")
    }

    override fun onTerminate() {
        Timber.d("onTerminate()")
        Timber.d("stopKoin()")
        stopKoin()
        super.onTerminate()
    }
}
