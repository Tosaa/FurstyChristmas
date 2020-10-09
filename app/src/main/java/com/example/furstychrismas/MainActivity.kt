package com.example.furstychrismas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.RoomDatabase
import com.example.furstychrismas.databinding.ActivityMainBinding
import com.example.furstychrismas.koin.dbModule
import com.example.furstychrismas.koin.myModule
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.util.Util
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.logging.Logger
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

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
}