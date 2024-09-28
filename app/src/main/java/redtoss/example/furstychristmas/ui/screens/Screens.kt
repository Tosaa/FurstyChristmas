package redtoss.example.furstychristmas.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate
import java.time.Month
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
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
                    popBackstackToOverview(navController)
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

    fun popBackstackToOverview(navController: NavHostController) {
        Timber.d("Navigation::popBackstackToOverview: from ${navController.currentBackStackEntry?.destination?.route}")
        navController.popBackStack("overview", inclusive = false)
    }
}