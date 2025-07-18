package uz.kabir.weather.domain.model

data class CurrentAirPollutionDomain(
    val lat: Double,
    val lon: Double,
    val aqi: Int,
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double,
    val timestamp: Long
)
