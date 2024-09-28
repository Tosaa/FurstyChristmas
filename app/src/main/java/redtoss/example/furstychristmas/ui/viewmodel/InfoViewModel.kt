package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase

class InfoViewModel(val loadInfoUseCase: LoadInfoUseCase, val dayCompletionUseCase: DayCompletionStatusUseCase) : ViewModel() {
    var date: LocalDate = LocalDate.now()
    private val daysInfo = flow {
        emit(loadInfoUseCase.getInfoAtDay(date))
    }
    val title = daysInfo.map { it?.title ?: "No Title" }
    val pages = daysInfo.map { it?.pages ?: emptyList() }
    val isDone = true
    fun setDateAsDone(day: LocalDate) {
        viewModelScope.launch { dayCompletionUseCase.markDayAsDone(day) }
    }
}