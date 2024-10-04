package redtoss.example.furstychristmas.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import redtoss.example.furstychristmas.ui.screens.DebugScreen
import redtoss.example.furstychristmas.ui.screens.Screen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.OVERVIEW.route(),
    finishActivity: ()->Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        listOf(
            Screen.OVERVIEW,
            Screen.EULA,
            Screen.CHRISTMAS,
            Screen.DAY_INFO,
            Screen.DAY_WORKOUT,
            Screen.EXERCISE,
            Screen.EXERCISE_OVERVIEW,
        ).forEach {
            composable(
                route = it.basicRoute,
                arguments = it.arguments
            ) { navBackStackEntry ->
                it.screen(navBackStackEntry, navController)
            }
        }
        composable(Screen.Dialog.CLOSE_APP.basicRoute) {
            Screen.Dialog.CLOSE_APP.screen(closeApp = finishActivity, doNotCloseApp = {navController.navigate(Screen.OVERVIEW.route())})
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