package uz.kabir.weather.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_table")
data class CityGeoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)