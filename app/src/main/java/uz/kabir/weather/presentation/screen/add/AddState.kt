package uz.kabir.weather.presentation.screen.add

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.CurrentWeatherDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.presentation.state.WeatherCurrentResult


data class AddState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cityGeoList: List<CityGeoDomain> = emptyList(),
    val getCurrentWeatherData: List<WeatherCurrentResult> = emptyList(),
    val savedCityGeoList: List<CityGeoDomain> = emptyList()
)