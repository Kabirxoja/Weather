package uz.kabir.weather.domain.usecase

import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.domain.repository.LocalRepository

class SaveSelectedCityUseCase(private val localRepository: LocalRepository) {
    suspend operator fun invoke(city: GeoInfoDomain) = localRepository.saveGeoInfo(city)
}