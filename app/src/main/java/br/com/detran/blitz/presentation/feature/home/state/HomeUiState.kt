package br.com.detran.blitz.presentation.feature.home.state

import br.com.detran.blitz.core.bottomsheet.BottomSheetLayout
import br.com.detran.blitz.core.enums.tab.Tab
import br.com.detran.blitz.presentation.model.blitz.Blitz

data class HomeUiState(
    val loading: Boolean = true,
    val tabs: List<Tab> = listOf(Tab.MAP, Tab.FINE),
    val tabSelected: Tab = Tab.MAP,
    val currentBottomSheet : BottomSheetLayout = BottomSheetLayout.PositionBlitzBottomSheet,
    val blitz : List<Blitz> = emptyList(),
    val showMarker: Boolean = false
)
