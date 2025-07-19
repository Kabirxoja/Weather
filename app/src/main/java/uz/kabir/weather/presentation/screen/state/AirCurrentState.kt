package uz.kabir.weather.presentation.screen.state

import uz.kabir.weather.domain.model.CurrentAirPollutionDomain

sealed class AirCurrentState{
    object Loading: AirCurrentState()
    data class Success(val data: CurrentAirPollutionDomain): AirCurrentState()
    data class Error(val message: String): AirCurrentState()
}