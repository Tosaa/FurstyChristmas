package com.example.furstychristmas.koin

import android.content.Context
import androidx.room.Room
import com.example.furstychristmas.persistence.DayDatabase
import com.example.furstychristmas.repository.AchievementRepository
import com.example.furstychristmas.repository.content.ContentUseCase
import com.example.furstychristmas.repository.content.InfoRepository
import com.example.furstychristmas.repository.content.WorkoutRepository
import com.example.furstychristmas.repository.day.AddDayCompletionUseCase
import com.example.furstychristmas.repository.day.DayCompletionRepository
import com.example.furstychristmas.repository.day.DayCompletionStatusUseCase
import com.example.furstychristmas.screen.day.WorkoutViewModel
import com.example.furstychristmas.screen.overview.CardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.time.LocalDate

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            DayDatabase::class.java,
            "database"
        ).fallbackToDestructiveMigration()
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
    single { InfoRepository() }
    single { DayCompletionRepository(get()) }

    single { ContentUseCase(get(), get()) }
    single { DayCompletionStatusUseCase(get()) }
    single { AddDayCompletionUseCase(get()) }


    viewModel { (date: LocalDate) ->
        WorkoutViewModel(
            date, get(), get()
        )
    }

    viewModel { CardViewModel(get()) }
}