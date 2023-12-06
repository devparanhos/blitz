package br.com.detran.blitz.presentation.feature.map.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import br.com.detran.blitz.R
import br.com.detran.blitz.core.constant.Key
import br.com.detran.blitz.core.function.bitmapDescriptorFromVector
import br.com.detran.blitz.presentation.model.blitz.Blitz
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    blitz: List<Blitz>,
    showMarker : Boolean,
    onSetBlitz: (latitude: Double, longitude: Double) -> Unit
){
    val context = LocalContext.current
    val mapCenter = LatLng(-15.797102, -47.8941508)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mapCenter,13f)
    }
    val markerState = rememberMarkerState(key = Key.BLITZ, position = cameraPositionState.position.target)

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = true
        )
    ) {
        val icon = bitmapDescriptorFromVector(context, R.drawable.officer)

        if (showMarker) {
            if (cameraPositionState.isMoving) {
                markerState.position = cameraPositionState.position.target
            }

            Marker(
                state = markerState,
                title = stringResource(id = R.string.button_label_click_to_add),
                onClick = {
                    onSetBlitz(
                        cameraPositionState.position.target.latitude,
                        cameraPositionState.position.target.longitude
                    )
                    true
                }
            )
        }

        blitz.forEach { blitz ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        blitz.latitude,
                        blitz.longitude
                    )
                ),
                icon = icon
            )
        }
    }
}