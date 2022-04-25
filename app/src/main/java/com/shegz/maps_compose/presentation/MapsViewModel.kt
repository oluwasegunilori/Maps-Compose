package com.shegz.maps_compose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.MapStyleOptions
import com.shegz.maps_compose.domain.model.ParkingSpot
import com.shegz.maps_compose.domain.repository.ParkingSpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: ParkingSpotRepository
) : ViewModel() {
    var state by mutableStateOf(MapState())


    init {

        viewModelScope.launch {
            repository.getParkingSpots().collectLatest {
                state = state.copy(
                    parkingSpots = it
                )
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json)
                    ),
                    isFalloutMap = state.isFalloutMap.not()
                )
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.delete(event.spot)
                }
            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insert(
                        ParkingSpot(
                            lat = event.latLng.latitude,
                            long = event.latLng.longitude
                        )
                    )
                }
            }
        }
    }

}