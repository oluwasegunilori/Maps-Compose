package com.shegz.maps_compose.domain.model

data class ParkingSpot(
    val lat: Double,
    val long: Double,
    val id: Long? = null
)