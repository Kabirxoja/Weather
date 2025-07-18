package uz.kabir.weather.domain.model

data class CurrentWeatherDomain(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val pressure: Int,
    val humidity: Int,
    val weatherMain: String,
    val weatherDescription: String,
    val icon: String,
    val windSpeed: Double,
    val windDegree: Int,
    val cloudiness: Int,
    val sunrise: Long,
    val sunset: Long,
    val lat: Double,
    val lon: Double
)
