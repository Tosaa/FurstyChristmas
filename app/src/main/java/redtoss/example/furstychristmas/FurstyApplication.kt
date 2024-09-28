package redtoss.example.furstychristmas

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

const val PREFERENCES_EULA_ACCEPTED = "eulaAccepted"
const val PREFERENCES_APP_VERSION = "version_code"

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
            activateAlarmIfInDecember()
        }
    }


    private suspend fun activateAlarmIfInDecember() {
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
            addDayCompletionUseCase.addDefaultEntriesForSeason(activeSeason)
        } else {
            Timber.d("setupDatabaseIfNecessary(): Entries for year ${today.year} / season ($activeSeason) exist already")
        }
    }

    private fun setRecurringAlarm(context: Context, dayForAlarm: LocalDate) {
        Timber.d("setRecurringAlarm()")
        var alarm = dayForAlarm.atTime(17, 30)
        if (System.currentTimeMillis() > alarm.toInstant(ZoneOffset.ofHours(1)).toEpochMilli()) {
            Timber.d("setRecurringAlarm(): change alarm: $alarm to tomorrow: ${alarm.plusDays(1)}")
            alarm = alarm.plusDays(1)
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
            alarm.toInstant(ZoneOffset.ofHours(1)).toEpochMilli(),
            AlarmManager.INTERVAL_DAY,
            pendingNotificationIntent
        )
        Timber.d("setRecurringAlarm(): set Wakeup Alarm for ($alarm)")
    }

    override fun onTerminate() {
        Timber.d("onTerminate()")
        Timber.d("stopKoin()")
        stopKoin()
        super.onTerminate()
    }
}
