package redtoss.example.furstychristmas.screen.day.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import java.time.LocalDate

class InfoViewModel(
    private val date: LocalDate,
    private val dayCompletionStatusUseCase: DayCompletionStatusUseCase,
    private val infoUseCase: LoadInfoUseCase
) : ViewModel() {

    private var info = MutableLiveData<InfoContent>()
    var isDone = MutableLiveData(false)
    var title = info.map { it.title }
    var pages = info.map { it.pages }

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