package uz.kabir.weather.domain.model

data class GeoInfoDomain(
    val city: String,
    val country: String,
    val lat: Double,
    val lon: Double
)