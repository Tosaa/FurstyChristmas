package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlin.random.Random
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.season

class OverviewViewModel(
    completionStatusUseCase: DayCompletionStatusUseCase
) : ViewModel() {
    val allCalendarCards = completionStatusUseCase.getDaysToCompleteForSeason(DateUtil.today().season()).distinctUntilChanged().map { it.shuffled(Random(79)) }.asFlow()
}
