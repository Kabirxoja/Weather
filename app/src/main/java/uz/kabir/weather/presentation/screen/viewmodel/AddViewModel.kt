package uz.kabir.weather.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.usecase.GetCityGeoUseCase
import uz.kabir.weather.domain.usecase.GetCurrentWeatherUseCase
import uz.kabir.weather.domain.usecase.GetGeoCodingUseCase
import uz.kabir.weather.domain.usecase.SaveCityGeoUseCase
import uz.kabir.weather.domain.usecase.SaveSelectedCityUseCase
import uz.kabir.weather.presentation.screen.intent.AddIntent
import uz.kabir.weather.presentation.screen.state.AddState
import uz.kabir.weather.presentation.screen.state.WeatherCurrentState

class AddViewModel(
    private val geoCodingUseCase: GetGeoCodingUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val saveCityGeoUseCase: SaveCityGeoUseCase,
    private val getCityGeoUseCase: GetCityGeoUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddState())
    val state: StateFlow<AddState> = _state.asStateFlow()

    private val intentFlow = MutableSharedFlow<AddIntent>()

    init {
        handleIntents()
    }

    fun sendIntent(intent: AddIntent) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    private fun handleIntents() {
        intentFlow
            .onEach { intent ->
                when (intent) {
                    is AddIntent.QueryChanged -> handleQueryChanged(intent.query)
                    is AddIntent.GetCurrentWeather -> { handleLoadSavedCityWeathers() }
                    is AddIntent.AddCityGeo -> {
                        handleAddCityGeo(intent.cityGeo)   //HUDDI shuni alohida funksiyaga ajratish kere (yana add qilib qoyvoti) // qara alohida DataSore saqlasin qilib
                    }
                    AddIntent.LoadCityGeo -> handleLoadCityGeo()
                    is AddIntent.SaveSelectedCity -> {
                        //DataStore Save
                        saveSelectedCityUseCase(intent.cityGeo)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleAddCityGeo(cityGeo: CityGeoDomain) {
        viewModelScope.launch {
            saveCityGeoUseCase(cityGeo)
//            handleLoadSavedCityWeathers() - agar yanglinmasa yana chaqirish (kerak emas instead Flow)
        }
    }

    private fun handleLoadCityGeo() {
        viewModelScope.launch {
            getCityGeoUseCase().collect { cityGeoList ->
                _state.update { it.copy(savedCityGeoList = cityGeoList) }
            }
        }
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun handleQueryChanged(query: String) {
        flowOf(query)
            .debounce(400)
            .distinctUntilChanged()
            .filter { it.length > 1 }
            .onStart {
                _state.update { it.copy(query = query, isLoading = true, errorMessage = null) }
            }
            .flatMapLatest { cityName ->
                flow {
                    val result = geoCodingUseCase(cityName)
                    emit(result)
                }
            }
            .onEach { result ->
                result.fold(
                    onSuccess = { list ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                cityGeoList = list,
                                errorMessage = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                cityGeoList = emptyList(),
                                errorMessage = error.message ?: "Unknown error"
                            )
                        }
                    }
                )
            }
            .launchIn(viewModelScope)
    }



    private fun handleLoadSavedCityWeathers() {
        viewModelScope.launch {
            getCityGeoUseCase().collectLatest { cities ->
                _state.update { it.copy(savedCityGeoList = cities) }

                val weatherList = cities.map { city ->
                    getCurrentWeatherUseCase(city.name).fold(
                        onSuccess = { WeatherCurrentState.Success(it) },
                        onFailure = { WeatherCurrentState.Error(it.message ?: "Unknown error") }
                    )
                }

                _state.update {
                    it.copy(getCurrentWeatherData = weatherList)
                }
            }
        }
    }
}