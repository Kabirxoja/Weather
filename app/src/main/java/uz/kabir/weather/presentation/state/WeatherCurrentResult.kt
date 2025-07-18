package uz.kabir.weather.presentation.state

import uz.kabir.weather.domain.model.CurrentWeatherDomain

sealed class WeatherCurrentResult {
    object Loading: WeatherCurrentResult()
    data class Success(val data: CurrentWeatherDomain): WeatherCurrentResult()
    data class Error(val message: String): WeatherCurrentResult()
}