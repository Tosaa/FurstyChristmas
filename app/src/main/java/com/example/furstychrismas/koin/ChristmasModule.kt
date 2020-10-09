package com.example.furstychrismas.koin

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.furstychrismas.model.Card
import com.example.furstychrismas.persistence.CardDao
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.screen.day.WorkoutViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
import com.example.furstychrismas.util.Util
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.logging.Logger
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CardDatabase::class.java,
            "database"
        )
                /*
            .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val scope = CoroutineScope(Job() + Dispatchers.IO)
                scope.launch {
                }
            }
        })*/
            .build()
    }
}

val myModule = module {

    factory { DayRepository(get()) }

    viewModel { (day: Int) ->
        WorkoutViewModel(
            day, get()
        )
    }

    viewModel<CardViewModel> { CardViewModel(get()) }
}