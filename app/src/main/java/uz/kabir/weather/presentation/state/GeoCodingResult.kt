package uz.kabir.weather.presentation.state

import uz.kabir.weather.domain.model.CityGeoDomain

sealed class GeoCodingResult {
    object Loading: GeoCodingResult()
    data class Success(val data: List<CityGeoDomain>): GeoCodingResult()
    data class Error(val message: String): GeoCodingResult()
}