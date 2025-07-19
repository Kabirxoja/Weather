package uz.kabir.weather.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.kabir.weather.presentation.screen.screen.AddScreen
import uz.kabir.weather.presentation.screen.screen.MainScreen

@Composable
fun AppNavGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = AppDestination.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(AppDestination.Main.route) {
            MainScreen(navController = navHostController)
        }
        composable(AppDestination.Add.route) {
            AddScreen(navController = navHostController)
        }
    }

}