package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.distinctUntilChanged
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase

class OverviewViewModel(
    completionStatusUseCase: DayCompletionStatusUseCase
) : ViewModel() {
    val allCalendarCards = completionStatusUseCase.getDaysToComplete.distinctUntilChanged().asFlow()
}