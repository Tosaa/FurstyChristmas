package com.example.furstychristmas.koin

import android.content.Context
import androidx.room.Room
import com.example.furstychristmas.persistence.CardDatabase
import com.example.furstychristmas.repository.AchievementRepository
import com.example.furstychristmas.repository.CardRepository
import com.example.furstychristmas.repository.WorkoutRepository
import com.example.furstychristmas.screen.day.WorkoutViewModel
import com.example.furstychristmas.screen.overview.CardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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

    // factory { DayRepository(get(), androidApplication().assets) }

    single {
        androidApplication().getSharedPreferences(
            "com.example.furstychristmas",
            Context.MODE_PRIVATE
        )
    }

    single { AchievementRepository() }
    single { WorkoutRepository(androidApplication().assets) }
    single { CardRepository(get()) }


    viewModel { (day: Int) ->
        WorkoutViewModel(
            day, get(), get()
        )
    }

    viewModel { CardViewModel(get()) }
}