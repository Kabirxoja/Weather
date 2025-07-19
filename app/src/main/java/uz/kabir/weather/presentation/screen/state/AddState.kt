package uz.kabir.weather.presentation.screen.state

import uz.kabir.weather.domain.model.CityGeoDomain

data class AddState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cityGeoList: List<CityGeoDomain> = emptyList(),
    val getCurrentWeatherData: List<WeatherCurrentState> = emptyList(),
    val savedCityGeoList: List<CityGeoDomain> = emptyList()
)