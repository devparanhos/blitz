package br.com.detran.blitz.presentation.feature.fine.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.detran.blitz.core.function.calculateDistanceBetweenPoints
import br.com.detran.blitz.presentation.feature.fine.action.FineAction
import br.com.detran.blitz.presentation.feature.fine.state.FineUiState
import br.com.detran.blitz.presentation.model.blitz.Blitz
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class FineViewModel(
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(FineUiState())
    val state = _state.asStateFlow()

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun triggerAction(action: FineAction) {
        when(action) {
            is FineAction.GetLocation -> {
                getLocation(blitz = action.blitz)
            }
        }
    }

    private fun getLocation(blitz: List<Blitz>) {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        checkNearBlitz(list = blitz, address = addresses?.get(0))
                    }
                }
            } catch (e: SecurityException) {
                _state.update { it.copy(loading = false) }

            }
        }
    }

    private fun checkNearBlitz(list: List<Blitz>, address: Address?) {
        if (list.isNotEmpty()) {
            address?.let {
                for (blitz in list){
                    if(
                        calculateDistanceBetweenPoints(
                            latBlitz = blitz.latitude,
                            longBlitz = blitz.longitude,
                            latLocation = address.latitude,
                            longLocation = address.longitude
                        ) <= 2.0
                    ) {
                        _state.update { state -> state.copy(nearBlitz = blitz) }
                        break
                    }
                }
            }
        }

        _state.update { state ->
            state.copy(
                loading = false,
                myLocation = address
            )
        }
    }
}