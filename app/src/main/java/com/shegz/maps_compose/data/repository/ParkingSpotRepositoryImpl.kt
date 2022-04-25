package com.shegz.maps_compose.data.repository

import com.shegz.maps_compose.data.ParkingSpotDao
import com.shegz.maps_compose.data.toParkingSpot
import com.shegz.maps_compose.data.toParkingSpotEntity
import com.shegz.maps_compose.domain.model.ParkingSpot
import com.shegz.maps_compose.domain.repository.ParkingSpotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParkingSpotRepositoryImpl @Inject constructor(
    private val parkingSpotDao: ParkingSpotDao
) : ParkingSpotRepository {
    override suspend fun insert(spot: ParkingSpot) {
        parkingSpotDao.insert(spot.toParkingSpotEntity())
    }

    override suspend fun delete(spot: ParkingSpot) {
        parkingSpotDao.delete(spot.toParkingSpotEntity())
    }

    override fun getParkingSpots(): Flow<List<ParkingSpot>> {
        return parkingSpotDao.getSparkingSpots().map {
            it.map {spots->
                spots.toParkingSpot()
            }
        }
    }
}