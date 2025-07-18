package uz.kabir.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityGeoDao {
    @Insert
    suspend fun insertWeather(weatherEntity: CityGeoEntity)

    @Query("select * from geo_table")
    fun getGeoCoordinates(): Flow<List<CityGeoEntity>>
}