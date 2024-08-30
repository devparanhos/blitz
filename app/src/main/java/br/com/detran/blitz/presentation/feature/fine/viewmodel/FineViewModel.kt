package br.com.detran.blitz.presentation.feature.fine.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import br.com.detran.blitz.core.function.calculateDistanceBetweenPoints
import br.com.detran.blitz.presentation.feature.fine.action.FineAction
import br.com.detran.blitz.presentation.feature.fine.state.FineUiState
import br.com.detran.blitz.presentation.model.blitz.Blitz

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import java.util.Locale

class FineViewModel(
    context: Context,
    blitz: List<Blitz>
) : ViewModel() {

    private val _state = MutableStateFlow(FineUiState())
    val state = _state.asStateFlow()

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val hasPermission = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    init {
        if (hasPermission) {
            getLocation(blitz = blitz, context = context)
        } else {
            _state.update { it.copy(loading = false) }
        }
    }

    fun triggerAction(action: FineAction) {
        when(action) {
            is FineAction.GetLocation -> {
                getLocation(blitz = action.blitz, context = action.context)
            }
        }
    }

    private fun getLocation(blitz: List<Blitz>, context: Context) {
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
                        ) <= 4.0
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