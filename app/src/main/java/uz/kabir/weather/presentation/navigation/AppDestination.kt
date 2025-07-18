package uz.kabir.weather.presentation.navigation

sealed class AppDestination(val route: String){
    object Splash: AppDestination("splash")
    object Main: AppDestination("main")
    object Add: AppDestination("add")
}