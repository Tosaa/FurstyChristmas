package com.example.furstychrismas.koin

import androidx.room.Room
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.repository.AchievementRepository
import com.example.furstychrismas.repository.CardRepository
import com.example.furstychrismas.repository.DateRepository
import com.example.furstychrismas.repository.WorkoutRepository
import com.example.furstychrismas.screen.day.WorkoutViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
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

    factory { AchievementRepository() }
    factory { DateRepository() }
    factory { WorkoutRepository(androidApplication().assets) }
    factory { CardRepository(get()) }


    viewModel { (day: Int) ->
        WorkoutViewModel(
            day, get(), get()
        )
    }

    viewModel { CardViewModel(get(), get()) }
}