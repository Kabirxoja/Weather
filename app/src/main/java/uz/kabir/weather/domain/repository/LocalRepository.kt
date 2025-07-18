package uz.kabir.weather.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.kabir.weather.data.remote.model.GeoInfoData
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain

interface LocalRepository{

    suspend fun insertCityGeo(city:CityGeoDomain)

    fun getCityGeo(): Flow<List<CityGeoDomain>>

    suspend fun saveGeoInfo(geo: GeoInfoDomain)
    fun getGeoInfo():Flow<GeoInfoDomain>


}