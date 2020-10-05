package com.example.furstychrismas.koin

import com.example.furstychrismas.screen.day.ExerciseViewModel
import com.example.furstychrismas.screen.overview.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    viewModel { (day: Int) ->
        ExerciseViewModel(
            day
        )
    }
    viewModel { CardViewModel() }
}