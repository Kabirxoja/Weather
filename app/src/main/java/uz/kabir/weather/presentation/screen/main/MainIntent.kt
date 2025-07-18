package uz.kabir.weather.presentation.screen.main

import uz.kabir.weather.domain.model.CityGeoDomain

sealed interface MainIntent{
    object LoadSelectedCity : MainIntent
    object LoadCurrentWeather : MainIntent
    object LoadCurrentAirPollution : MainIntent
}