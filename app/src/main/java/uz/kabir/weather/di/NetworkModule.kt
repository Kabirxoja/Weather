package uz.kabir.weather.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.kabir.weather.data.remote.api.WeatherApiService
import uz.kabir.weather.data.remote.network.NetworkChecker

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(WeatherApiService::class.java)
    }

    single {
        NetworkChecker(get())
    }

}

