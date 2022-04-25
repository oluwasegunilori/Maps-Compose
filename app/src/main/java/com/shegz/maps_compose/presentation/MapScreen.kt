package com.shegz.maps_compose.presentation

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val cameraPositionState = rememberCameraPositionState{
    }


    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(MapEvent.ToggleFalloutMap)
            }) {
                Icon(
                    imageVector =
                    if (viewModel.state.isFalloutMap) {
                        Icons.Default.ToggleOff
                    } else {
                        Icons.Default.ToggleOn
                    }, contentDescription = "Toggle Fallout map"
                )

            }
        }
    ) {
        Column {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = viewModel.state.properties.copy(
                    isMyLocationEnabled = locationPermissionState.allPermissionsGranted,
                ),
                uiSettings = uiSettings.value,
                onMapLongClick = {
                    viewModel.onEvent(MapEvent.OnMapLongClick(it))
                },
//                cameraPositionState = CameraPositionState(
//                    position = CameraPosition(
//                        LatLng(locationPermissionState.)
//                    )
//                )
            ) {

                Polyline(points =
                viewModel.state.parkingSpots.map {
                    LatLng(it.lat, it.long)
                },
                color = Color.Red,
                geodesic = true,)

                viewModel.state.parkingSpots.map { spot ->

                    Marker(
                        position = LatLng(spot.lat, spot.long),
                        title = "Parking spot (${spot.lat}, ${spot.long})",
                        snippet = "Long click to delete",
                        onInfoWindowClick = {
                            viewModel.onEvent(
                                MapEvent.OnInfoWindowLongClick(spot)
                            )
                        },
                        onClick = {
                            it.showInfoWindow()
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN
                        )
                    )
                }
            }


        }
        if (!locationPermissionState.allPermissionsGranted)
            RequireLocationPermission(locationPermissionState = locationPermissionState)

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequireLocationPermission(locationPermissionState: MultiplePermissionsState) {

    if (!locationPermissionState.allPermissionsGranted) {
        val allPermissionsRevoked =
            locationPermissionState.permissions.size ==
                    locationPermissionState.revokedPermissions.size
        Column(
            modifier = Modifier
                .background(Color.Transparent.copy(alpha = 0.6f))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    locationPermissionState.launchMultiplePermissionRequest()
                },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 20.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                ),
                modifier =
                Modifier
                    .fillMaxWidth(0.9f)
                    .clip(shape = RoundedCornerShape(bottomStartPercent = 50, topEndPercent = 50))

            ) {
                Column {
                    Text(
                        textAlign = TextAlign.Center,
                        text =
                        if (!allPermissionsRevoked) {
                            "Yay! Thanks for letting me access your approximate location. " +
                                    "But you know what would be great? If you allow me to know where you " +
                                    "exactly are. Thank you!"
                        } else if (locationPermissionState.shouldShowRationale) {
                            // Both location permissions have been denied
                            "Getting your exact location is important for this app. " +
                                    "Please grant us fine location. Thank you :D"
                        } else {
                            // First time the user sees this feature or the user doesn't want to be asked again
                            "This feature requires location permission"
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Tap to accept permission",
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }

}
