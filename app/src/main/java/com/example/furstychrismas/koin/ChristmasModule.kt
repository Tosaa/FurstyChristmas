package com.example.furstychrismas.koin

import com.example.furstychrismas.CardViewModel
import com.example.furstychrismas.ExerciseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    viewModel { (day: Int) -> ExerciseViewModel(day) }
    viewModel { CardViewModel() }
}