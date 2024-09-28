package redtoss.example.furstychristmas.koin

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import redtoss.example.furstychristmas.domain.day.repository.DayCompletionRepository
import redtoss.example.furstychristmas.domain.day.usecase.AddDayCompletionUseCase
import redtoss.example.furstychristmas.domain.day.usecase.ContentTypeUseCase
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.info.util.InfoJsonParser
import redtoss.example.furstychristmas.domain.workout.repository.ExerciseRepository
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.domain.workout.usecase.LoadExerciseUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.domain.workout.util.Exercise2020JsonParser
import redtoss.example.furstychristmas.domain.workout.util.ExerciseJsonParser
import redtoss.example.furstychristmas.domain.workout.util.WorkoutJsonParser
import redtoss.example.furstychristmas.persistence.DayDatabase
import redtoss.example.furstychristmas.ui.viewmodel.ChristmasViewModel
import redtoss.example.furstychristmas.ui.viewmodel.ExerciseOverviewViewModel
import redtoss.example.furstychristmas.ui.viewmodel.ExerciseViewModel
import redtoss.example.furstychristmas.ui.viewmodel.InfoViewModel
import redtoss.example.furstychristmas.ui.viewmodel.OverviewViewModel
import redtoss.example.furstychristmas.ui.viewmodel.WorkoutViewModel

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
            "redtoss.example.furstychristmas",
            Context.MODE_PRIVATE
        )
    }

    single { Exercise2020JsonParser(androidApplication().assets) }
    single { ExerciseJsonParser() }
    single { WorkoutJsonParser() }
    single { InfoJsonParser(androidApplication().assets) }

    single { ExerciseRepository(get(), androidApplication().assets) }
    single { WorkoutRepository(get(), get(), get(), androidApplication().assets) }
    single { InfoRepository(get(), androidApplication().assets) }
    single { DayCompletionRepository(get()) }

    single { LoadExerciseUseCase(get()) }
    single { LoadWorkoutUseCase(get()) }
    single { LoadInfoUseCase(get()) }
    single { DayCompletionStatusUseCase(get()) }
    single { AddDayCompletionUseCase(get()) }
    single { ContentTypeUseCase(get(), get()) }

    viewModel { OverviewViewModel(get()) }
    viewModel { InfoViewModel(get(), get()) }
    viewModel { WorkoutViewModel(get(), get()) }
    viewModel { ExerciseViewModel(get()) }
    viewModel { ChristmasViewModel(get(), get()) }
    viewModel { ExerciseOverviewViewModel(get()) }
}
