package com.shegz.maps_compose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParkingSpotEntity(
    @ColumnInfo(name = "latitude")
    val lat: Double,
    @ColumnInfo(name = "longitude")
    val lon: Double,
    @PrimaryKey
    val id: Long? = null
)
{

}