package com.example.furstychrismas.koin

import com.example.furstychrismas.repository.DayRepository
import com.example.furstychrismas.screen.day.WorkoutViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    single { DayRepository() }

    viewModel { (day: Int) ->
        WorkoutViewModel(
            day, get()
        )
    }
    viewModel { CardViewModel() }
}