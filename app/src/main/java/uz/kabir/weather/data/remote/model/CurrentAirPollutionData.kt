package uz.kabir.weather.data.remote.model

data class CurrentAirPollutionData(
    val coord: Coord,
    val list: List<AirPollutionData>
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class AirPollutionData(
    val main: AirQualityIndex,
    val components: AirComponents,
    val dt: Long
)

data class AirQualityIndex(
    val aqi: Int
)

data class AirComponents(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double
)
