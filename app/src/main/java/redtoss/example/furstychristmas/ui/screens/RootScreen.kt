package redtoss.example.furstychristmas.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.Month
import org.koin.compose.koinInject
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.PREFERENCES_EULA_ACCEPTED
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.ui.MyAppNavHost
import redtoss.example.furstychristmas.util.DateUtil
import timber.log.Timber

@Composable
fun RootScreen(finishActivity: () -> Unit) {
    val navController: NavHostController = rememberNavController()
    Scaffold(topBar = { TopBar(navController) }) { paddingValues ->
        MyAppNavHost(modifier = Modifier.padding(paddingValues), navController = navController, finishActivity = finishActivity)
    }
    val preferences = koinInject<SharedPreferences>()
    val isEulaAccepted = preferences.getBoolean(PREFERENCES_EULA_ACCEPTED, false)
    LaunchedEffect(isEulaAccepted) {
        if (!isEulaAccepted) {
            Timber.d("checkEula(): Eula has not been Accepted yet -> show Eula")
            navController.navigate(Screen.EULA.route(R.raw.eula2021))
        }
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    MyAppBar(
        onBackIconClicked = {
            val backStackIsNotEmpty = navController.popBackStack()
            if (!backStackIsNotEmpty) {
                navController.navigate(Screen.Dialog.CLOSE_APP.route())
            }
        },
        onEditClicked = {
            Timber.d("Navigation::onNavigateToDebug")
            if (BuildConfig.DEBUG) {
                navController.navigate("debug")
            }
        },
        onInfoClicked = {
            navController.navigate(Screen.EULA.route(R.raw.eula2021))
        },
        onSportClicked = {
            navController.navigate(Screen.EXERCISE_OVERVIEW.route())
        }
    )
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
