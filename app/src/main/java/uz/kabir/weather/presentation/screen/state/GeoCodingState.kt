package uz.kabir.weather.presentation.screen.state

import uz.kabir.weather.domain.model.CityGeoDomain

sealed class GeoCodingState {
    object Loading: GeoCodingState()
    data class Success(val data: List<CityGeoDomain>): GeoCodingState()
    data class Error(val message: String): GeoCodingState()
}