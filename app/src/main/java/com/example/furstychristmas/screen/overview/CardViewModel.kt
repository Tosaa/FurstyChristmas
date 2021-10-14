package com.example.furstychristmas.screen.overview

import androidx.lifecycle.ViewModel
import com.example.furstychristmas.repository.day.DayCompletionStatusUseCase

class CardViewModel(
    completionStatusUseCase: DayCompletionStatusUseCase
) : ViewModel() {
    private val allDaysToComplete = completionStatusUseCase.getDaysToComplete

    val cards = allDaysToComplete

}