package uz.kabir.weather.presentation.screen.main

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.presentation.state.AirCurrentResult
import uz.kabir.weather.presentation.state.WeatherCurrentResult

data class MainState(
    val isLoading: Boolean = true,
    val noInternet: Boolean = false,
    val noCitySelected: Boolean = false,
    val error: String? = null
)