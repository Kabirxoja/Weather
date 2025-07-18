package uz.kabir.weather.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.kabir.weather.data.remote.model.GeoInfoData
import uz.kabir.weather.domain.model.CityGeoDomain

class SelectedCityDataStore(private val dataStore: DataStore<Preferences>) {
    private val gson = Gson()
    private val selectedCityKey = stringPreferencesKey("selected_city")

    suspend fun saveSelectedCity(city: GeoInfoData) {
        val json = gson.toJson(city)
        dataStore.edit { it[selectedCityKey] = json }
    }

    fun getSelectedCity(): Flow<GeoInfoData?> = dataStore.data.map { prefs ->
        prefs[selectedCityKey]?.let { gson.fromJson(it, GeoInfoData::class.java) }

    }

}