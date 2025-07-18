package uz.kabir.weather.presentation.screen.main

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.presentation.state.AirCurrentResult
import uz.kabir.weather.presentation.state.WeatherCurrentResult

sealed interface MainState {
    object Loading : MainState
    object Error : MainState
    data class Success(val city: GeoInfoDomain) : MainState

    data class CurrentWeather(val data: WeatherCurrentResult) : MainState
    data class CurrentAirPollution(val data: AirCurrentResult) : MainState
}