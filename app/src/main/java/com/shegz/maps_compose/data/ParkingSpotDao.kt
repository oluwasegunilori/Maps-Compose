package com.shegz.maps_compose.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingSpotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spot: ParkingSpotEntity)

    @Delete
    suspend fun delete(spot: ParkingSpotEntity)

    @Query("SELECT * FROM parkingspotentity")
    fun getSparkingSpots(): Flow<List<ParkingSpotEntity>>
}