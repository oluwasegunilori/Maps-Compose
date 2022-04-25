package com.shegz.maps_compose.data

import com.shegz.maps_compose.domain.model.ParkingSpot

fun ParkingSpotEntity.toParkingSpot(): ParkingSpot{
    return ParkingSpot(
        lat = lat,
        long = lon,
        id = id
    )
}

fun ParkingSpot.toParkingSpotEntity(): ParkingSpotEntity{
    return ParkingSpotEntity(
        lat = lat,
        lon = long,
        id = id
    )
}