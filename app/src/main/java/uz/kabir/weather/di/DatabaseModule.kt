package uz.kabir.weather.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.kabir.weather.data.datastore.SelectedCityDataStore
import uz.kabir.weather.data.local.CityGeoDao
import uz.kabir.weather.data.local.WeatherDatabase
import uz.kabir.weather.domain.usecase.GetSelectedCityUseCase
import uz.kabir.weather.domain.usecase.SaveSelectedCityUseCase

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), WeatherDatabase::class.java, "weather_db").build()
    }

    single<CityGeoDao> {
        val database: WeatherDatabase = get()
        database.dao
    }

    single<DataStore<Preferences>>{
        PreferenceDataStoreFactory.create{
            androidContext().preferencesDataStoreFile("geo_coordinate_data")
        }
    }
    // DataStore Wrapper
    single { SelectedCityDataStore(get()) }

    // UseCases
    factory { SaveSelectedCityUseCase(get()) }
    factory { GetSelectedCityUseCase(get()) }

}