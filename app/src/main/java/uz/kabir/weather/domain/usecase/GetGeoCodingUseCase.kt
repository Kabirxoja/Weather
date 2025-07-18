package uz.kabir.weather.domain.usecase

import uz.kabir.weather.data.remote.network.NetworkChecker
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.repository.NetworkRepository
import uz.kabir.weather.presentation.state.GeoCodingResult

class GetGeoCodingUseCase(
    private val networkRepository: NetworkRepository,
    private val networkChecker: NetworkChecker
) {
    suspend operator fun invoke(cityName: String): Result<List<CityGeoDomain>> {
        return try {
            if (!networkChecker.isNetworkConnected()) {
                Result.failure(Exception("No internet connection"))
            } else {
                val data = networkRepository.getGeoCoding(cityName)
                Result.success(data)
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }
}
