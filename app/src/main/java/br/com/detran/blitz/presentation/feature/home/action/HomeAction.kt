package br.com.detran.blitz.presentation.feature.home.action

import android.content.Context
import br.com.detran.blitz.core.enums.tab.Tab
import br.com.detran.blitz.presentation.model.blitz.Blitz

sealed class HomeAction {
    data class UpdateShowMarker(val show: Boolean) : HomeAction()
    data class UpdateTabSelected(val tab: Tab, val showMarker: Boolean = false) : HomeAction()
    data class CreateBlitz(val blitz: Blitz) : HomeAction()
    data class OpenCreateBlitzForm(val latitude: Double, val longitude: Double, val context: Context) : HomeAction()
}
