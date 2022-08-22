package redtoss.example.furstychristmas

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import redtoss.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.eula.EulaActivity
import redtoss.example.furstychristmas.koin.dbModule
import redtoss.example.furstychristmas.koin.myModule
import redtoss.example.furstychristmas.receiver.DailyNotificationReceiver
import redtoss.example.furstychristmas.ui.theme.FurstyChrismasTheme
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivityCompose : ComponentActivity() {
    private val preferences: SharedPreferences by inject()
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase by inject()
    private val addDayCompletionUseCase: AddDayCompletionUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(dbModule, myModule)
        }

        setupDatabaseIfNecessary()
        checkEula()
        val today = DateUtil.today()
        if (DateUtil.isDateInRange(today, DateUtil.firstDayForAlarm, DateUtil.lastDayForAlarm)) {
            Timber.d("set alarm for $today")
            setRecurringAlarm(applicationContext, today)
        }

        preferences.getString("developer_date", null)?.let {
            val date = LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
            DateUtil.setDevDay(date)
        }

        setContent {
            FurstyChrismasTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }


    private fun setupDatabaseIfNecessary() {
        Timber.d("setupDatabaseIfNecessary()")
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            if (!dayCompletionStatusUseCase.isDatabaseSetup) {
                val today = DateUtil.today()
                Timber.d("setupDatabaseIfNecessary(): Add Entries to Database for this year (${today.year})")
                val days = IntRange(1, 24).map { day -> LocalDate.of(today.year, Month.DECEMBER, day) }.toList()
                addDayCompletionUseCase.addDefaultEntriesForDates(days)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
        stopKoin()
    }

    private fun checkEula() {
        Timber.d("checkEula()")
        val isEulaAccepted = preferences.getBoolean("eulaAccepted", false)
        if (!isEulaAccepted) {
            Timber.d("checkEula(): Eula has not been Accepted yet -> show Eula")

            val eulaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                Timber.d("onEulaResult: $it")
                // finish()
                }
            }
            val intent = Intent(this, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula2021
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            eulaResultLauncher.launch(intent)
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
            0, repeatedNotificationIntent, PendingIntent.FLAG_CANCEL_CURRENT
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

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FurstyChrismasTheme {
        Greeting("Android")
    }
}