package uz.kabir.weather.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.kabir.weather.data.datastore.SelectedCityDataStore
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.domain.repository.LocalRepository

class GetSelectedCityUseCase(private val localRepository: LocalRepository) {
    operator fun invoke(): Flow<GeoInfoDomain?> = localRepository.getGeoInfo()
}