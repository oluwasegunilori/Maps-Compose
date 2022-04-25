package com.shegz.maps_compose.domain.repository

import com.shegz.maps_compose.domain.model.ParkingSpot
import kotlinx.coroutines.flow.Flow

interface ParkingSpotRepository {
    suspend fun insert(spot: ParkingSpot)
    suspend fun delete(spot: ParkingSpot)
    fun getParkingSpots(): Flow<List<ParkingSpot>>
}