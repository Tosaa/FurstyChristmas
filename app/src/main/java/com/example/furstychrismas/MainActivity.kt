package com.example.furstychrismas

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.furstychrismas.databinding.ActivityMainBinding
import com.example.furstychrismas.eula.EulaActivity
import com.example.furstychrismas.koin.dbModule
import com.example.furstychrismas.koin.myModule
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.receiver.DailyNotificationReceiver
import com.example.furstychrismas.repository.DateRepository
import com.example.furstychrismas.util.Util
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
import java.time.Month
import java.time.ZoneOffset


class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.toolbar.setNavigationOnClickListener {
            binding.navHostFragment.findNavController().navigateUp()
        }
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(dbModule, myModule)
        }

        val prefs: SharedPreferences by inject()
        preferences = prefs
        if (preferences.getBoolean("first app start", true)) {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                if (get<CardDatabase>().cardDao().tableSize() == 0) {
                    Util.createDaysInDB(get())
                    preferences.edit().putBoolean("first app start", false).apply()
                }
            }
        }
        checkEula()
        val dayRepository: DateRepository by inject()
        val today = dayRepository.todayMapped()
        if ((today.dayOfMonth in 1..24) && today.month == Month.DECEMBER) {
            Timber.d("set alarm for $today")
            setRecurringAlarm(applicationContext, today)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val dateRepository: DateRepository by inject()
        dateRepository.updateTime()
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