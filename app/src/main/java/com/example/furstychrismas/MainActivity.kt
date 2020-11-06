package com.example.furstychrismas

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.furstychrismas.databinding.ActivityMainBinding
import com.example.furstychrismas.eula.EulaActivity
import com.example.furstychrismas.koin.dbModule
import com.example.furstychrismas.koin.myModule
import com.example.furstychrismas.persistence.CardDatabase
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
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        startKoin {
            androidLogger(Level.DEBUG)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }


    private fun checkEula() {
        val eulaAccepted = preferences.getBoolean("eulaAccepted", false)
        if (!eulaAccepted) {
            val intent = Intent(this, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }
    }
}