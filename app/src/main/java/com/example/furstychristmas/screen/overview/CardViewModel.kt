package com.example.furstychristmas.screen.overview

import androidx.lifecycle.ViewModel
import com.example.furstychristmas.day.domain.usecase.DayCompletionStatusUseCase

class CardViewModel(
    completionStatusUseCase: DayCompletionStatusUseCase
) : ViewModel() {
    private val allDaysToComplete = completionStatusUseCase.getDaysToComplete

    val cards = allDaysToComplete

}