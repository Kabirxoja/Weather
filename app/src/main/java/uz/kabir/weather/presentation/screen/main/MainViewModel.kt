package uz.kabir.weather.presentation.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.domain.usecase.GetCurrentAirPollutionUseCase
import uz.kabir.weather.domain.usecase.GetCurrentWeatherUseCase
import uz.kabir.weather.domain.usecase.GetSelectedCityUseCase
import uz.kabir.weather.presentation.screen.main.MainState.*
import uz.kabir.weather.presentation.state.AirCurrentResult
import uz.kabir.weather.presentation.state.WeatherCurrentResult

class MainViewModel(
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentAirPollutionUseCase: GetCurrentAirPollutionUseCase
) : ViewModel() {

    private val _cityState = MutableStateFlow<GeoInfoDomain?>(null)
    val cityState: StateFlow<GeoInfoDomain?> = _cityState.asStateFlow()

    private val _weatherState = MutableStateFlow<WeatherCurrentResult>(WeatherCurrentResult.Loading)
    val weatherState: StateFlow<WeatherCurrentResult> = _weatherState.asStateFlow()

    private val _airPollutionState = MutableStateFlow<AirCurrentResult>(AirCurrentResult.Loading)
    val airPollutionState: StateFlow<AirCurrentResult> = _airPollutionState.asStateFlow()

    fun onIntent(intent: MainIntent) {
        when (intent) {

            MainIntent.LoadSelectedCity -> viewModelScope.launch {
                getSelectedCityUseCase().collectLatest { city ->
                    _cityState.value = city
                    Log.d("MainViewModel", "Selected city: $city")

                    onIntent(MainIntent.LoadCurrentWeather)
                    onIntent(MainIntent.LoadCurrentAirPollution)
                }
            }

            MainIntent.LoadCurrentWeather -> viewModelScope.launch {

                val cityName = _cityState.value?.city
                Log.d("MainViewModel", "City name: $cityName")

                if (cityName != null) {
                    val result = getCurrentWeatherUseCase(cityName)
                    result
                        .onSuccess { data ->
                            _weatherState.value = WeatherCurrentResult.Success(data)
                            Log.d("DARAS", "DATA: $data")

                        }
                        .onFailure { e ->
                            _weatherState.value = WeatherCurrentResult.Error(e.message ?: "Unknown error")
                        }
                } else {
                    _weatherState.value = WeatherCurrentResult.Error("City not selected")
                }
            }

                is MainIntent.LoadCurrentAirPollution -> {
                    viewModelScope.launch {
                        val lat = _cityState.value?.lat
                        val lon = _cityState.value?.lon

                        if (lat != null && lon != null) {
                            val result = getCurrentAirPollutionUseCase(
                                lat = lat.toString(),
                                lon = lon.toString()
                            )

                            result.fold(
                                onSuccess = { data ->
                                    _airPollutionState.value = AirCurrentResult.Success(data)
                                },
                                onFailure = { throwable ->
                                    _airPollutionState.value =
                                        AirCurrentResult.Error(throwable.message ?: "Unknown error")
                                }
                            )
                        } else {
                            _airPollutionState.value = AirCurrentResult.Error("Location not available")
                        }
                    }
                }
        }
    }
}
