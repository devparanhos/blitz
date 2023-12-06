package br.com.detran.blitz.presentation.feature.home.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.detran.blitz.core.bottomsheet.BottomSheetLayout
import br.com.detran.blitz.domain.usecase.blitz.CreateBlitzUseCase
import br.com.detran.blitz.domain.usecase.blitz.GetBlitzUseCase
import br.com.detran.blitz.presentation.model.blitz.Blitz
import br.com.detran.blitz.presentation.feature.home.action.HomeAction
import br.com.detran.blitz.presentation.feature.home.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale

class HomeViewModel(
    private val getBlitzUseCase: GetBlitzUseCase,
    private val createBlitzUseCase: CreateBlitzUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        getBlitz()
    }

    fun triggerAction(action: HomeAction) {
        when(action) {
            is HomeAction.CreateBlitz -> {
                createBlitz(blitz = action.blitz)
            }

            is HomeAction.UpdateShowMarker -> {
                if (action.show) {
                    _state.update {
                        it.copy(
                            currentBottomSheet = BottomSheetLayout.PositionBlitzBottomSheet,
                            showMarker = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            showMarker = false
                        )
                    }
                }
            }

            is HomeAction.UpdateTabSelected -> {
                _state.update {
                    it.copy(
                        tabSelected = action.tab,
                        showMarker = false
                    )
                }
            }

            is HomeAction.OpenCreateBlitzForm -> {
                openBlitzForm(
                    latitude = action.latitude,
                    longitude = action.longitude,
                    context = action.context
                )
            }
        }
    }

    private fun createBlitz(blitz: Blitz) {
        viewModelScope.launch {
            try {
                createBlitzUseCase.create(blitz = blitz)
                getBlitz()
            } catch (e: Exception) {
                Log.i("Error", "Apresentar log de erro")
            }
        }
    }

    private fun getBlitz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    blitz = getBlitzUseCase.getBlitz(),
                    loading = false,
                    showMarker = false
                )
            }
        }
    }

    private fun openBlitzForm(latitude: Double, longitude: Double, context: Context) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocation(latitude, longitude, 1)

        _state.update {
            it.copy(
                currentBottomSheet = BottomSheetLayout.AddBlitzBottomSheet(
                    address = address?.get(0),
                    latitude = latitude,
                    longitude = longitude
                )
            )
        }
    }
}