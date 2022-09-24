package redtoss.example.furstychristmas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.R
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
    startDestination: String = "overview",
    infoUseCase: LoadInfoUseCase = get(),
    workoutUseCase: LoadWorkoutUseCase = get(),
) {
    Column {
        MyAppBar(onBackIconClicked = { navController.popBackStack() },
            onEditClicked = {
                Timber.d("Navigation::onNavigateToDebug")
                if (BuildConfig.DEBUG) {
                    navController.navigate("debug")
                }
            },
            onInfoClicked = {}
        )
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
                            Timber.d("Navigation::onNavigateToCard: $date")
                            infoUseCase.getInfoAtDay(date)?.let {
                                Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Info'")
                                navController.navigate("calendar/${date.toEpochDay()}/info")
                                return@launch
                            }
                            workoutUseCase.getWorkoutAtDay(date)?.let {
                                Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Workout'")
                                navController.navigate("calendar/${date.toEpochDay()}/workout")
                                return@launch
                            }
                        }
                    }
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
                        onClose = {
                            Timber.d("Navigation::onClose")
                            popBackstackToOverview(navController)
                        })
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
                        onClose = {
                            Timber.d("Navigation::onClose")
                            popBackstackToOverview(navController)
                        },
                        onExerciseClicked = { exerciseId ->
                            Timber.d("Navigation::onExerciseClicked")
                            navController.navigate("exercise/$exerciseId")
                        },
                        onDayCompletedClicked = { completedDay ->
                            Timber.d("Navigation::onDayCompletedClicked")
                            popBackstackToOverview(navController)
                        })
                }
            }
            composable(
                "exercise/{exercise}",
                arguments = listOf(navArgument("exercise") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("exercise")?.let {
                    ExercisePreview(onClose = {
                        Timber.d("Navigation::onClose")
                        Timber.d("Navigation::popBackStack: from ${navController.currentBackStackEntry?.id}")
                        navController.popBackStack()
                    }, selectedExercise = it)
                }
            }
            composable(
                "debug",
            ) {
                DebugScreen(
                    context = LocalContext.current,
                )
            }
        }
    }
}

private fun popBackstackToOverview(navController: NavHostController) {
    Timber.d("Navigation::popBackstackToOverview: from ${navController.currentBackStackEntry?.destination?.route}")
    navController.popBackStack("overview", inclusive = false)
}

@Composable
fun MyAppBar(
    onBackIconClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onEditClicked: () -> Unit,
) {
    val date = DateUtil.todayAsLiveData.asFlow().collectAsState(initial = LocalDate.now())
    val motto = if (BuildConfig.DEBUG) date.value.toString() else stringResource(id = R.string.motto)
    TopAppBar(
        title = {
            Text(text = motto)
        },
        navigationIcon = {
            IconButton(onClick = onBackIconClicked, Modifier.padding(start = 5.dp)) {
                Icon(Icons.Filled.ArrowBack, "back button")
            }
        },
        actions = {
            IconButton(onClick = { onInfoClicked() }) {
                Icon(Icons.Filled.Info, "infoButton")
            }
            IconButton(onClick = { onEditClicked() }) {
                Icon(Icons.Filled.Edit, "editButton")
            }
        }
    )
}