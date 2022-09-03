package redtoss.example.furstychristmas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.ui.screens.*
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate


@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "overview"
) {
    val infoUseCase: LoadInfoUseCase = get()
    val workoutUseCase: LoadWorkoutUseCase = get()
    val dayCompletionUseCase: DayCompletionStatusUseCase = get()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("overview") {
            val overviewScope = rememberCoroutineScope()
            OverviewScreen(
                onNavigateToCard = { date ->
                    overviewScope.launch {
                        Timber.d("onNavigateToCard: $date")
                        infoUseCase.getInfoAtDay(date)?.let {
                            Timber.d("Content on Day: $date is 'Info'")
                            navController.navigate("calendar/${date.toEpochDay()}/info")
                            return@launch
                        }
                        workoutUseCase.getWorkoutAtDay(date)?.let {
                            Timber.d("Content on Day: $date is 'Workout'")
                            navController.navigate("calendar/${date.toEpochDay()}/workout")
                            return@launch
                        }
                    }
                },
                onNavigateToDebug = {
                    if (BuildConfig.DEBUG) {
                        navController.navigate("debug")
                    }
                },
            )
        }
        composable(
            "calendar/{date}/info",
            arguments = listOf(navArgument("date") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("date")?.let {
                val day = LocalDate.ofEpochDay(it)
                CalendarInfoDay(
                    day = day,
                    onClose = { navController.popBackStack("overview", inclusive = false) })
            }
        }
        composable(
            "calendar/{date}/workout",
            arguments = listOf(navArgument("date") { type = NavType.LongType })
        ) { backStackEntry ->
            val workoutScope = rememberCoroutineScope()
            backStackEntry.arguments?.getLong("date")?.let {
                val day = LocalDate.ofEpochDay(it)
                CalendarWorkoutDay(
                    day = day,
                    onClose = { navController.popBackStack("overview", inclusive = false) },
                    onExerciseClicked = { exerciseId -> navController.navigate("exercise/$exerciseId") },
                    onDayCompletedClicked = { completedDay ->
                        workoutScope.launch {
                            dayCompletionUseCase.markDayAsDone(completedDay)
                        }
                        navController.popBackStack("overview", inclusive = false)
                    })
            }
        }
        composable(
            "exercise/{exercise}",
            arguments = listOf(navArgument("exercise") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("exercise")?.let {
                ExercisePreview(onClose = { navController.popBackStack() })
            }
        }
        composable(
            "debug",
        ) {
            DebugScreen(
                context = LocalContext.current,
                onClose = { navController.popBackStack("overview", inclusive = false) },
                currentDebugDate = DateUtil.today()
            )
        }
    }
}