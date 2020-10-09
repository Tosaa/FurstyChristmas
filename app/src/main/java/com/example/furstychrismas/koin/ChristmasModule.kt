package com.example.furstychrismas.koin

import androidx.room.Room
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.screen.day.WorkoutViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
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

    factory { DayRepository(get()) }

    viewModel { (day: Int) ->
        WorkoutViewModel(
            day, get()
        )
    }

    viewModel<CardViewModel> { CardViewModel(get()) }
}