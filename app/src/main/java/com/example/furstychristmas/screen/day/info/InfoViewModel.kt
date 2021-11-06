package com.example.furstychristmas.screen.day.info

import android.text.Html
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import com.example.furstychristmas.domain.info.model.InfoContent
import com.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class InfoViewModel(
    private val date: LocalDate,
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase,
    private val infoUseCase: LoadInfoUseCase
) : ViewModel() {

    private var info = MutableLiveData<InfoContent>()
    var isDone = MutableLiveData(false)
    var title = info.map { it.title }
    var image = info.map { it.pages[0] }.map { it.imageid }
    var text = info.map { it.pages[0] }.map { Html.fromHtml(it.text,Html.FROM_HTML_MODE_COMPACT) }

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