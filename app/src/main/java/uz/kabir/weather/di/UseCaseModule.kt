package uz.kabir.weather.di

import org.koin.dsl.module
import uz.kabir.weather.domain.usecase.GetCityGeoUseCase
import uz.kabir.weather.domain.usecase.GetCurrentAirPollutionUseCase
import uz.kabir.weather.domain.usecase.GetCurrentWeatherUseCase
import uz.kabir.weather.domain.usecase.GetGeoCodingUseCase
import uz.kabir.weather.domain.usecase.GetSelectedCityUseCase
import uz.kabir.weather.domain.usecase.SaveCityGeoUseCase
import uz.kabir.weather.domain.usecase.SaveSelectedCityUseCase

val useCaseModule = module {
    single { GetGeoCodingUseCase(get(), get()) }
    single { GetCurrentWeatherUseCase(get(), get()) }
    single { GetCityGeoUseCase(get()) }
    single { SaveCityGeoUseCase(get()) }
    single { SaveSelectedCityUseCase(get()) }
    single { GetSelectedCityUseCase(get()) }
    single { GetCurrentAirPollutionUseCase(get(), get()) }
    single { GetCurrentAirPollutionUseCase(get(), get()) }


}