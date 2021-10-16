package com.example.furstychristmas.util

import androidx.navigation.NavController
import com.example.furstychristmas.info.domain.usecase.LoadInfoUseCase
import com.example.furstychristmas.screen.overview.CardsOverviewFragmentDirections
import com.example.furstychristmas.workout.domain.usecase.LoadWorkoutUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

class NavigationHelper(private val navController: NavController, private val loadInfoUseCase: LoadInfoUseCase, private val loadWorkoutUseCase: LoadWorkoutUseCase, private val coroutineScope: CoroutineScope) {

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
}