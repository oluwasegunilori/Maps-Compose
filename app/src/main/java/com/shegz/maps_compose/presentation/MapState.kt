package com.shegz.maps_compose.presentation

import com.google.maps.android.compose.MapProperties
import com.shegz.maps_compose.domain.model.ParkingSpot

data class MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val isFalloutMap: Boolean = false
)
