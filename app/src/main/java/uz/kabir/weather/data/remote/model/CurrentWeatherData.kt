package uz.kabir.weather.data.remote.model

data class CurrentWeatherData(
    val coord: CoordDto,
    val weather: List<WeatherDto>,
    val main: MainDto,
    val wind: WindDto,
    val clouds: CloudsDto,
    val sys: SysDto,
    val name: String
)

data class CoordDto(
    val lon: Double,
    val lat: Double
)

data class WeatherDto(
    val main: String,
    val description: String,
    val icon: String
)

data class MainDto(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WindDto(
    val speed: Double,
    val deg: Int
)

data class CloudsDto(
    val all: Int
)

data class SysDto(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
