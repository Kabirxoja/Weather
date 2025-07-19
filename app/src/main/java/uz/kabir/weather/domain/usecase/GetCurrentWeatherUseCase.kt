package uz.kabir.weather.domain.usecase

import uz.kabir.weather.data.remote.network.NetworkChecker
import uz.kabir.weather.domain.model.CurrentWeatherDomain
import uz.kabir.weather.domain.repository.NetworkRepository

class GetCurrentWeatherUseCase(
    private val networkRepository: NetworkRepository,
    private val networkChecker: NetworkChecker
) {
    suspend operator fun invoke(cityName: String): Result<CurrentWeatherDomain> {
        return if (networkChecker.isNetworkConnected()) {
            try {
                val data = networkRepository.getCurrentWeather(cityName)
                Result.success(data)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
