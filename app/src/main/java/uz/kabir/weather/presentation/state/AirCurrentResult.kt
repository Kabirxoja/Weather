package uz.kabir.weather.presentation.state

import uz.kabir.weather.domain.model.CurrentAirPollutionDomain

sealed class AirCurrentResult{
    object Loading: AirCurrentResult()
    data class Success(val data: CurrentAirPollutionDomain): AirCurrentResult()
    data class Error(val message: String): AirCurrentResult()
}