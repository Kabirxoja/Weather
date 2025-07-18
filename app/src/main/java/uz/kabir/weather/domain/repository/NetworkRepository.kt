package uz.kabir.weather.domain.repository

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.CurrentAirPollutionDomain
import uz.kabir.weather.domain.model.CurrentWeatherDomain

interface NetworkRepository {

    suspend fun getGeoCoding(cityName:String): List<CityGeoDomain>

    suspend fun getCurrentWeather(cityName:String): CurrentWeatherDomain

    suspend fun getCurrentAirPollution(latitude:String, longitude:String): CurrentAirPollutionDomain



}