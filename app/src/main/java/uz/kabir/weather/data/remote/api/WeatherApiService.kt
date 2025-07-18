package uz.kabir.weather.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.kabir.weather.data.remote.model.CityGeoData
import uz.kabir.weather.data.remote.model.CurrentAirPollutionData
import uz.kabir.weather.data.remote.model.CurrentWeatherData

interface WeatherApiService {
    @GET("geo/1.0/direct")
    suspend fun getGeoCoding(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<CityGeoData>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherData

    @GET("data/2.5/air_pollution")
    suspend fun getCurrentAirPollution(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): CurrentAirPollutionData
}