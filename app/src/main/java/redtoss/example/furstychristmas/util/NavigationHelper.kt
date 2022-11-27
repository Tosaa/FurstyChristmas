package redtoss.example.furstychristmas.util

import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase

class NavigationHelper(private val navController: NavController, private val loadInfoUseCase: LoadInfoUseCase, private val loadWorkoutUseCase: LoadWorkoutUseCase, private val coroutineScope: CoroutineScope) {

    /*
    fun navigateToDay(date: LocalDate) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                when {
                    date.month == Month.DECEMBER && date.dayOfMonth == 24 -> CardsOverviewFragmentDirections.actionCardsOverviewFragmentToLastDay()
                    loadInfoUseCase.getInfoAtDay(date) != null -> CardsOverviewFragmentDirections.overviewInfoPreview(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    loadWorkoutUseCase.getWorkoutAtDay(date) != null -> CardsOverviewFragmentDirections.overviewWorkoutPreview(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    else -> null
                }?.let { action -> navController.navigate(action) }
            }
        }
    }
     */
}
