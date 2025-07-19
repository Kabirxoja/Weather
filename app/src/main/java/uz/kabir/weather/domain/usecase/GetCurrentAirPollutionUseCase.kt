package uz.kabir.weather.domain.usecase

import uz.kabir.weather.data.remote.network.NetworkChecker
import uz.kabir.weather.domain.model.CurrentAirPollutionDomain
import uz.kabir.weather.domain.repository.NetworkRepository

class GetCurrentAirPollutionUseCase(
    private val networkRepository: NetworkRepository,
    private val networkChecker: NetworkChecker
) {
    suspend operator fun invoke(lat: String, lon: String): Result<CurrentAirPollutionDomain> {
        return try {
            if (!networkChecker.isNetworkConnected()) {
                Result.failure(Exception("No internet connection"))
            } else {
                val data = networkRepository.getCurrentAirPollution(lat, lon)
                Result.success(data)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}