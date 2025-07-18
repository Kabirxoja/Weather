package uz.kabir.weather.presentation.screen.add

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain

sealed class AddIntent {
    data class QueryChanged(val query: String) : AddIntent()
    data class GetCurrentWeather(val cityName: String) : AddIntent()

    data class AddCityGeo(val cityGeo: CityGeoDomain): AddIntent()
    object LoadCityGeo: AddIntent()

    data class SaveSelectedCity(val cityGeo: GeoInfoDomain): AddIntent()


}