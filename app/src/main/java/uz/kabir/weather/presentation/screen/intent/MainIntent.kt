package uz.kabir.weather.presentation.screen.intent

sealed interface MainIntent{
    object LoadSelectedCity : MainIntent
    object LoadCurrentWeather : MainIntent
    object LoadCurrentAirPollution : MainIntent
    object LoadInitialData : MainIntent
}