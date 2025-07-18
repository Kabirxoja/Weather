package uz.kabir.weather.data.mapper

import uz.kabir.weather.data.local.CityGeoEntity
import uz.kabir.weather.data.remote.model.CityGeoData
import uz.kabir.weather.data.remote.model.CurrentAirPollutionData
import uz.kabir.weather.data.remote.model.CurrentWeatherData
import uz.kabir.weather.data.remote.model.GeoInfoData
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.CurrentAirPollutionDomain
import uz.kabir.weather.domain.model.CurrentWeatherDomain
import uz.kabir.weather.domain.model.GeoInfoDomain

fun CityGeoData.toDomain(): CityGeoDomain {
    return CityGeoDomain(
        name = this.name,
        country = this.country,
        state = this.state,
        lat = this.lat,
        lon = this.lon
    )
}


fun CurrentWeatherData.toDomain(): CurrentWeatherDomain {
    val firstWeather = weather.firstOrNull()
    return CurrentWeatherDomain(
        cityName = name,
        country = sys.country,
        temperature = main.temp,
        feelsLike = main.feels_like,
        minTemp = main.temp_min,
        maxTemp = main.temp_max,
        pressure = main.pressure,
        humidity = main.humidity,
        weatherMain = firstWeather?.main.orEmpty(),
        weatherDescription = firstWeather?.description.orEmpty(),
        icon = firstWeather?.icon.orEmpty(),
        windSpeed = wind.speed,
        windDegree = wind.deg,
        cloudiness = clouds.all,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        lat = coord.lat,
        lon = coord.lon,
    )
}

fun CityGeoDomain.toEntity(): CityGeoEntity {
    return CityGeoEntity(
        name = this.name,
        country = this.country,
        state = this.state,
        lat = this.lat,
        lon = this.lon
    )
}

fun CityGeoEntity.toDomain(): CityGeoDomain {
    return CityGeoDomain(
        name = this.name,
        country = this.country,
        state = this.state,
        lat = this.lat,
        lon = this.lon
    )
}

fun GeoInfoData.toDomain(): GeoInfoDomain {
    return GeoInfoDomain(
        city = this.city,
        lat = this.lat,
        lon = this.lon,
        country = this.country
    )
}

fun GeoInfoDomain.toEntity(): GeoInfoData {
    return GeoInfoData(
        city = this.city,
        lat = this.lat,
        lon = this.lon,
        country = this.country
    )
}

fun CurrentAirPollutionData.toDomain(): CurrentAirPollutionDomain {
    return CurrentAirPollutionDomain(
        lat = coord.lat,
        lon = coord.lon,
        aqi = list.first().main.aqi,
        co = list.first().components.co,
        no = list.first().components.no,
        no2 = list.first().components.no2,
        o3 = list.first().components.o3,
        so2 = list.first().components.so2,
        pm2_5 = list.first().components.pm2_5,
        pm10 = list.first().components.pm10,
        nh3 = list.first().components.nh3,
        timestamp = list.first().dt
    )
}