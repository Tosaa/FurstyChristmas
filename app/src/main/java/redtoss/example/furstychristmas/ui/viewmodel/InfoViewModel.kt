package redtoss.example.furstychristmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import java.time.LocalDate

class InfoViewModel(val loadInfoUseCase: LoadInfoUseCase) : ViewModel() {
    var date: LocalDate = LocalDate.now()
    private val daysInfo = flow {
        emit(loadInfoUseCase.getInfoAtDay(date))
    }
    val title = daysInfo.map { it?.title ?: "No Title" }
    val pages = daysInfo.map { it?.pages }
}