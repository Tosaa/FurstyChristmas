package com.example.furstychrismas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.furstychrismas.databinding.ActivityMainBinding
import com.example.furstychrismas.koin.dbModule
import com.example.furstychrismas.koin.myModule
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(dbModule, myModule)
        }
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            if (get<CardDatabase>().cardDao().tableSize() == 0) {
                Util.createDaysInDB(get())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}