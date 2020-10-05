package com.example.furstychrismas.koin

import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.screen.day.ExerciseViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    single { DayRepository() }

    viewModel { (day: Int) ->
        ExerciseViewModel(
            day, get()
        )
    }
    viewModel { CardViewModel() }
}