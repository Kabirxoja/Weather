package uz.kabir.weather.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.kabir.weather.BuildConfig
import uz.kabir.weather.data.local.CityGeoDao
import uz.kabir.weather.data.local.CityGeoEntity
import uz.kabir.weather.data.remote.api.WeatherApiService
import uz.kabir.weather.data.mapper.toDomain
import uz.kabir.weather.data.mapper.toEntity
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.CurrentAirPollutionDomain
import uz.kabir.weather.domain.model.CurrentWeatherDomain
import uz.kabir.weather.domain.repository.NetworkRepository

class NetworkRepositoryImp(private val apiService: WeatherApiService) :
    NetworkRepository {
    val key = BuildConfig.API_KEY

    // API -> Domain
    override suspend fun getGeoCoding(cityName: String): List<CityGeoDomain> {
        return apiService.getGeoCoding(cityName = cityName, apiKey = key).map { it.toDomain() }
        Log.d("GGGGFFFF", "getGeoCoding $cityName")
    }

    // API -> Domain
    override suspend fun getCurrentWeather(cityName: String): CurrentWeatherDomain {
        return apiService.getCurrentWeather(cityName = cityName, apiKey = key).toDomain()
        Log.d("GGGGFFFF", "getCurrentWeather: $cityName")
    }

    override suspend fun getCurrentAirPollution(
        latitude: String,
        longitude: String
    ): CurrentAirPollutionDomain {
        return apiService.getCurrentAirPollution(latitude = latitude, longitude = longitude, apiKey = key).toDomain()
        Log.d("GGGGFFFF", "getCurrentAirPollution: $latitude-$longitude")

    }
}