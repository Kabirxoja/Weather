package uz.kabir.weather.domain.model

data class CityGeoDomain(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)