package uz.kabir.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityGeoEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val dao: CityGeoDao
}