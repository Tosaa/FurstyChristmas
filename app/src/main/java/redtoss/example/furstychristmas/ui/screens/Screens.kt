package redtoss.example.furstychristmas.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate
import java.time.Month
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.domain.day.usecase.ContentTypeUseCase
import timber.log.Timber

sealed class Screen(val basicRoute: String, val arguments: List<NamedNavArgument>) {
    @Composable
    abstract fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController)

    object OVERVIEW : Screen("overview", emptyList()) {
        fun route(): String = basicRoute

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            val overviewScope = rememberCoroutineScope()
            val contentTypeUseCase: ContentTypeUseCase = koinInject()
            OverviewScreen(
                onNavigateToCard = { date ->
                    overviewScope.launch {
                        contentTypeUseCase.getTypeForDate(date).let {
                            return@launch when {
                                it == ContentTypeUseCase.Type.INFO -> {
                                    Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Info'")
                                    navController.navigate(DAY_INFO.route(date))
                                }

                                it == ContentTypeUseCase.Type.WORKOUT -> {
                                    Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Workout'")
                                    navController.navigate(DAY_WORKOUT.route(date))
                                }

                                date.month == Month.DECEMBER && date.dayOfMonth == 24 -> {
                                    Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Christmas'")
                                    navController.navigate(CHRISTMAS.route(date.year))
                                }

                                else -> {
                                    Timber.w("card was clicked but it was not christmas neither workout or info was provided for that day")
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    object EULA : Screen("eula/{eulaResource}", listOf(navArgument("eulaResource") { type = NavType.IntType })) {
        fun route(eulaResource: Int): String = "eula/$eulaResource"

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            navBackStackEntry.arguments?.getInt("eulaResource")?.let { eula ->
                EulaScreen(eulaResource = eula, onClose = {
                    Timber.d("Navigation::onClose")
                    if (it) {
                        popBackstackToOverview(navController)
                    } else {
                        Timber.d("EULA not agreed // Do nothing")
                    }
                })
            }
        }
    }

    object CHRISTMAS : Screen("calendar/christmas/{year}", listOf(navArgument("year") { type = NavType.IntType })) {
        fun route(year: Int): String = "calendar/christmas/$year"

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            navBackStackEntry.arguments?.getInt("year")?.let { year ->
                ChristmasDay(
                    year = year,
                    onClose = {
                        Timber.d("Navigation::onClose")
                        popBackstackToOverview(navController)
                    }
                )
            }
        }
    }

    object DAY_INFO : Screen("calendar/{date}/info", listOf(navArgument("date") { type = NavType.LongType })) {
        fun route(date: LocalDate): String = "calendar/${date.toEpochDay()}/info"

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            navBackStackEntry.arguments?.getLong("date")?.let {
                val day = LocalDate.ofEpochDay(it)
                CalendarInfoDay(
                    day = day,
                    onClose = {
                        Timber.d("Navigation::onClose")
                        popBackstackToOverview(navController)
                    })
            }
        }
    }

    object DAY_WORKOUT : Screen("calendar/{date}/workout", listOf(navArgument("date") { type = NavType.LongType })) {
        fun route(date: LocalDate): String = "calendar/${date.toEpochDay()}/workout"

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            navBackStackEntry.arguments?.getLong("date")?.let {
                val day = LocalDate.ofEpochDay(it)
                CalendarWorkoutDay(
                    day = day,
                    onClose = {
                        Timber.d("Navigation::onClose")
                        popBackstackToOverview(navController)
                    },
                    onExerciseClicked = { exerciseId ->
                        Timber.d("Navigation::onExerciseClicked")
                        navController.navigate(EXERCISE.route(exerciseId))
                    },
                    onDayCompletedClicked = { completedDay ->
                        Timber.d("Navigation::onDayCompletedClicked")
                        popBackstackToOverview(navController)
                    })
            }
        }
    }

    object EXERCISE : Screen("exercise/{exercise}", listOf(navArgument("exercise") { type = NavType.StringType })) {
        fun route(exercise: String): String = "exercise/$exercise"

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            navBackStackEntry.arguments?.getString("exercise")?.let {
                ExercisePreview(onClose = {
                    Timber.d("Navigation::onClose")
                    Timber.d("Navigation::popBackStack: from ${navController.currentBackStackEntry?.id}")
                    navController.popBackStack()
                }, selectedExercise = it)
            }
        }
    }

    object EXERCISE_OVERVIEW : Screen("exercisesOverview", emptyList()) {
        fun route(): String = basicRoute

        @Composable
        override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {
            ExerciseOverviewScreen(onExerciseClicked = {
                navController.navigate("exercise/${it.exerciseId}")
            })
        }
    }

    object Dialog {
        object CLOSE_APP : Screen("closeapp", emptyList()) {
            fun route(): String = basicRoute

            @Composable
            fun screen(closeApp: () -> Unit, doNotCloseApp: () -> Unit) {
                Column(Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.calendar_dialog_close_app_dialog_title), modifier = Modifier.fillMaxWidth(), fontSize = 26.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        Button(
                            {
                                Timber.d("denied to close app")
                                doNotCloseApp()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                        ) {
                            Text(stringResource(R.string.calendar_dialog_common_deny), fontSize = 20.sp)
                        }
                        Button(
                            {
                                Timber.d("confirmed to close app")
                                closeApp()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                        ) {
                            Text(stringResource(R.string.calendar_dialog_common_confirm), fontSize = 20.sp)
                        }
                    }
                }
            }

            @Composable
            override fun screen(navBackStackEntry: NavBackStackEntry, navController: NavHostController) {

            }

        }
    }

    fun popBackstackToOverview(navController: NavHostController) {
        Timber.d("Navigation::popBackstackToOverview: from ${navController.currentBackStackEntry?.destination?.route}")
        navController.popBackStack(route = OVERVIEW.route(), inclusive = false)
    }
}