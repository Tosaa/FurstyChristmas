package com.example.furstychristmas.koin

import android.content.Context
import androidx.room.Room
import com.example.furstychristmas.domain.day.repository.DayCompletionRepository
import com.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import com.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import com.example.furstychristmas.domain.info.repository.InfoRepository
import com.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import com.example.furstychristmas.domain.info.util.InfoJsonParser
import com.example.furstychristmas.domain.workout.repository.WorkoutRepository
import com.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import com.example.furstychristmas.domain.workout.util.Exercise2020JsonParser
import com.example.furstychristmas.domain.workout.util.ExerciseJsonParser
import com.example.furstychristmas.domain.workout.util.WorkoutJsonParser
import com.example.furstychristmas.persistence.DayDatabase
import com.example.furstychristmas.screen.day.info.InfoViewModel
import com.example.furstychristmas.screen.day.workout.WorkoutViewModel
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

    single { Exercise2020JsonParser(androidApplication().assets) }
    single { ExerciseJsonParser(androidApplication().assets) }
    single { WorkoutJsonParser(androidApplication().assets) }
    single { InfoJsonParser(androidApplication().assets, resources = androidApplication().resources) }

    single { WorkoutRepository(get(), get(), get()) }
    single { InfoRepository(get()) }
    single { DayCompletionRepository(get()) }

    single { LoadWorkoutUseCase(get()) }
    single { LoadInfoUseCase(get()) }
    single { DayCompletionStatusUseCase(get()) }
    single { AddDayCompletionUseCase(get()) }


    viewModel { (date: LocalDate) ->
        WorkoutViewModel(
            date, get(), get()
        )
    }
    viewModel { (date: LocalDate) ->
        InfoViewModel(
            date, get(), get()
        )
    }

    viewModel { CardViewModel(get()) }
}