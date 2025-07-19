package uz.kabir.weather.presentation.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.kabir.weather.data.remote.network.NetworkChecker
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.domain.usecase.GetCurrentAirPollutionUseCase
import uz.kabir.weather.domain.usecase.GetCurrentWeatherUseCase
import uz.kabir.weather.domain.usecase.GetSelectedCityUseCase
import uz.kabir.weather.presentation.screen.main.MainState.*
import uz.kabir.weather.presentation.state.AirCurrentResult
import uz.kabir.weather.presentation.state.AirCurrentResult.*
import uz.kabir.weather.presentation.state.WeatherCurrentResult
import uz.kabir.weather.presentation.state.WeatherCurrentResult.*

class MainViewModel(
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentAirPollutionUseCase: GetCurrentAirPollutionUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _cityState = MutableStateFlow<GeoInfoDomain?>(null)
    val cityState: StateFlow<GeoInfoDomain?> = _cityState.asStateFlow()

    private val _weatherState = MutableStateFlow<WeatherCurrentResult>(WeatherCurrentResult.Loading)
    val weatherState: StateFlow<WeatherCurrentResult> = _weatherState.asStateFlow()

    private val _airPollutionState = MutableStateFlow<AirCurrentResult>(AirCurrentResult.Loading)
    val airPollutionState: StateFlow<AirCurrentResult> = _airPollutionState.asStateFlow()

    private val _pollutionLevels = MutableStateFlow<Map<String, PollutionLevel>>(emptyMap())
    val pollutionLevels: StateFlow<Map<String, PollutionLevel>> = _pollutionLevels.asStateFlow()

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()


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
                            _weatherState.value =
                                WeatherCurrentResult.Error(e.message ?: "Unknown error")
                        }
                } else {
                    _weatherState.value = WeatherCurrentResult.Error("City not selected")
                }
            }

            is MainIntent.LoadCurrentAirPollution -> viewModelScope.launch {
                val (lat, lon) = _cityState.value?.let { it.lat to it.lon } ?: (null to null)
                if (lat == null || lon == null) {
                    _airPollutionState.value = AirCurrentResult.Error("Location not available")
                    return@launch
                }

                getCurrentAirPollutionUseCase(lat.toString(), lon.toString()).fold(
                    onSuccess = { data ->
                        _airPollutionState.value = AirCurrentResult.Success(data)
                        Log.d("DARAS", "DATA: $data")

                        // -------- CLASSIFY every pollutant ----------
                        _pollutionLevels.value = mapOf(
                            "PM2.5" to data.pm2_5.pm25Level(),
                            "PM10" to data.pm10.pm10Level(),
                            "NO₂" to data.no2.no2Level(),
                            "O₃" to data.o3.o3Level(),
                            "CO" to data.co.coLevel(),
                            "SO₂" to data.so2.so2Level()
                            // add NH₃ if you wish (no widely used breakpoints)
                        )
                    },
                    onFailure = { e ->
                        _airPollutionState.value =
                            AirCurrentResult.Error(e.message ?: "Unknown error")
                        _pollutionLevels.value = emptyMap()        // clear levels
                    }
                )
            }

            MainIntent.LoadInitialData -> {
                loadInitialData()
            }
        }
    }


    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                val hasInternet = networkChecker.isNetworkConnected()
                val city = getSelectedCityUseCase().firstOrNull()
                if (!hasInternet) {
                    _state.value = MainState(isLoading = false, noInternet = true)
                    return@launch
                }

                if (city!!.city.isNullOrBlank() || city.lat == 0.0 || city.lon == 0.0) {
                    _state.value = MainState(isLoading = false, noCitySelected = true)
                    return@launch
                } else {
                    _state.value = MainState(isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = MainState(isLoading = false, error = e.message)
            }
        }
    }
}
