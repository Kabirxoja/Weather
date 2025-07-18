package uz.kabir.weather.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.repository.LocalRepository

class GetCityGeoUseCase(private val localRepository: LocalRepository) {
    operator fun invoke(): Flow<List<CityGeoDomain>> {
        return localRepository.getCityGeo()
    }
}