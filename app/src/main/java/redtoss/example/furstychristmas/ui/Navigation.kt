package redtoss.example.furstychristmas.ui

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
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
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.domain.day.usecase.ContentTypeUseCase
import redtoss.example.furstychristmas.eula.EulaActivity
import redtoss.example.furstychristmas.ui.screens.*
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import org.koin.compose.koinInject

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "overview",
    contentTypeUseCase: ContentTypeUseCase = koinInject(),
) {
    val context = LocalContext.current
    Column {
        MyAppBar(
            onBackIconClicked = { navController.popBackStack() },
            onEditClicked = {
                Timber.d("Navigation::onNavigateToDebug")
                if (BuildConfig.DEBUG) {
                    navController.navigate("debug")
                }
            },
            onInfoClicked = {
                val intent = Intent(context, EulaActivity::class.java)
                val bundle = Bundle()
                val eula = R.raw.eula2021
                bundle.putInt("eula", eula)
                intent.putExtras(bundle)
                context.startActivity(intent)
            },
            onSportClicked = {
                navController.navigate("exercisesOverview")
            }
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
                            contentTypeUseCase.getTypeForDate(date).let {
                                return@launch when {
                                    it == ContentTypeUseCase.Type.INFO -> {
                                        Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Info'")
                                        navController.navigate("calendar/${date.toEpochDay()}/info")
                                    }
                                    it == ContentTypeUseCase.Type.WORKOUT -> {
                                        Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Workout'")
                                        navController.navigate("calendar/${date.toEpochDay()}/workout")
                                    }
                                    date.month == Month.DECEMBER && date.dayOfMonth == 24 -> {
                                        Timber.d("Navigation::onNavigateToCard: Content on Day: $date is 'Christmas'")
                                        navController.navigate("calendar/christmas/${date.year}")
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
            composable(
                "calendar/christmas/{year}",
                arguments = listOf(navArgument("year") { type = NavType.IntType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt("year")?.let { year ->
                    ChristmasDay(
                        year = year,
                        onClose = {
                            Timber.d("Navigation::onClose")
                            popBackstackToOverview(navController)
                        }
                    )
                }
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
            composable(
                "exercisesOverview",
            ) {
                ExerciseOverviewScreen(onExerciseClicked = {
                    navController.navigate("exercise/${it.exerciseId}")
                })
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
    onSportClicked: () -> Unit,
) {
    val date = DateUtil.todayAsLiveData.asFlow().collectAsState(initial = LocalDate.now())
    val motto = if (BuildConfig.DEBUG) {
        date.value.toString()
    } else {
        if (date.value.month in listOf(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)) {
            stringResource(id = R.string.motto, (date.value.year + 1).toString().takeLast(2))
        } else {
            stringResource(id = R.string.motto, date.value.year.toString().takeLast(2))
        }
    }
    TopAppBar(
        title = {
            Text(text = motto)
        },
        navigationIcon = {
            IconButton(onClick = onBackIconClicked, Modifier.padding(start = 5.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "back button")
            }
        },
        actions = {
            IconButton(onClick = { onInfoClicked() }) {
                Icon(Icons.Filled.Info, "infoButton")
            }
            IconButton(onClick = { onSportClicked() }) {
                Icon(Icons.AutoMirrored.Filled.List, "exercisesButton")
            }
            if (BuildConfig.DEBUG) {
                IconButton(onClick = { onEditClicked() }) {
                    Icon(Icons.Filled.Edit, "editButton")
                }
            }
        }
    )
}
