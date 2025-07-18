package uz.kabir.weather.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.kabir.weather.data.datastore.SelectedCityDataStore
import uz.kabir.weather.data.local.CityGeoDao
import uz.kabir.weather.data.mapper.toDomain
import uz.kabir.weather.data.mapper.toEntity
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.domain.repository.LocalRepository

class LocalRepositoryImp(
    private val dao: CityGeoDao,
    private val selectedCityDataStore: SelectedCityDataStore
) : LocalRepository {
    // Domain -> Entity
    override suspend fun insertCityGeo(city: CityGeoDomain) {
        return dao.insertWeather(city.toEntity())
    }

    // Room Entity -> Domain
    override fun getCityGeo(): Flow<List<CityGeoDomain>> {
        return dao.getGeoCoordinates().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveGeoInfo(geo: GeoInfoDomain) {
        selectedCityDataStore.saveSelectedCity(geo.toEntity())
    }

    override fun getGeoInfo(): Flow<GeoInfoDomain> {
        return selectedCityDataStore.getSelectedCity().map { it?.toDomain() ?: GeoInfoDomain("", "", 0.0, 0.0) }
    }

}