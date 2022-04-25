package com.shegz.maps_compose.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ParkingSpotEntity::class],
    version = 1,
    exportSchema = true
)

abstract class ParkingSpotDatabase : RoomDatabase(){
    abstract val dao: ParkingSpotDao
}