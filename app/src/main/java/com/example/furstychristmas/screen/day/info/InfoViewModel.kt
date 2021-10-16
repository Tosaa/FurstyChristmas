package com.example.furstychristmas.screen.day.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furstychristmas.day.domain.usecase.DayCompletionStatusUseCase
import com.example.furstychristmas.info.domain.model.InfoContent
import com.example.furstychristmas.info.domain.usecase.LoadInfoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class InfoViewModel(
    private val date: LocalDate,
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase,
    private val infoUseCase: LoadInfoUseCase
) : ViewModel() {

    private var info = MutableLiveData<InfoContent>()
    var isDone = MutableLiveData(false)

    init {
        viewModelScope.launch { info.postValue(infoUseCase.getInfoAtDay(date)) }
        viewModelScope.launch { isDone.postValue(dayCompletionStatusUseCase.isDayDone(date)) }
    }

    // workout end

    fun markAsDone() {
        viewModelScope.launch {
            dayCompletionStatusUseCase.markDayAsDone(date)
        }
    }

}