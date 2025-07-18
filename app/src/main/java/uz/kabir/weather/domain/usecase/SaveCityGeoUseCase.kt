package uz.kabir.weather.domain.usecase

import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.repository.LocalRepository

class SaveCityGeoUseCase (private val localRepository: LocalRepository) {
    suspend operator fun invoke(city: CityGeoDomain) {
        localRepository.insertCityGeo(city)
    }
}