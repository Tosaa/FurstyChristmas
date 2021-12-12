package redtoss.example.furstychristmas.screen.overview

import androidx.lifecycle.ViewModel
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase

class CardViewModel(
    completionStatusUseCase: DayCompletionStatusUseCase
) : ViewModel() {
    private val allDaysToComplete = completionStatusUseCase.getDaysToComplete

    val cards = allDaysToComplete

}