package uz.kabir.weather.presentation.screen.state

import uz.kabir.weather.domain.model.CurrentWeatherDomain

sealed class WeatherCurrentState {
    object Loading: WeatherCurrentState()
    data class Success(val data: CurrentWeatherDomain): WeatherCurrentState()
    data class Error(val message: String): WeatherCurrentState()
}