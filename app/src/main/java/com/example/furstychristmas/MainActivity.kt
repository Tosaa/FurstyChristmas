package com.example.furstychristmas

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.furstychristmas.databinding.ActivityMainBinding
import com.example.furstychristmas.eula.EulaActivity
import com.example.furstychristmas.koin.dbModule
import com.example.furstychristmas.koin.myModule
import com.example.furstychristmas.persistence.CardDatabase
import com.example.furstychristmas.receiver.DailyNotificationReceiver
import com.example.furstychristmas.util.DateUtil
import com.example.furstychristmas.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.time.LocalDate
import java.time.ZoneOffset


class MainActivity : AppCompatActivity() {
    private val preferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar(binding)
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(dbModule, myModule)
        }

        setupDatabaseOnFirstLaunch()
        checkEula()
        val today = DateUtil.today()
        if (DateUtil.isDateInRange(today, DateUtil.firstDayForAlarm, DateUtil.lastDayForAlarm)) {
            Timber.d("set alarm for $today")
            setRecurringAlarm(applicationContext, today)
        }
    }

    private fun setupDatabaseOnFirstLaunch() {
        //todo check how to init DB on first launch of db
        if (preferences.getBoolean("first app start", true)) {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                if (get<CardDatabase>().cardDao().tableSize() == 0) {
                    Util.createDaysInDB(get())
                    preferences.edit().putBoolean("first app start", false).apply()
                }
            }
        }
    }

    private fun setupToolbar(binding: ActivityMainBinding) {
        binding.toolbar.setNavigationOnClickListener {
            binding.navHostFragment.findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode != Activity.RESULT_OK) {
                    finish()
                }
            }
        }
    }

    private fun checkEula() {
        val eulaAccepted = preferences.getBoolean("eulaAccepted", false)
        if (!eulaAccepted) {
            val intent = Intent(this, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula2
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }
    }

    private fun setRecurringAlarm(context: Context, dayForAlarm: LocalDate) {

        var updateTime = dayForAlarm.atTime(17, 30).toInstant(ZoneOffset.ofHours(1)).toEpochMilli()
        val now = System.currentTimeMillis()
        if (now > updateTime) {
            Timber.d("change Day to tommorow:$now - $updateTime")
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
        Timber.d("set Wakeup Alarm for ${updateTime}")

    }
}