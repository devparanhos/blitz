package br.com.detran.blitz.core.bottomsheet

import android.location.Address

sealed class BottomSheetLayout {
    object PositionBlitzBottomSheet : BottomSheetLayout()
    data class AddBlitzBottomSheet(
        val address: Address?,
        val latitude: Double,
        val longitude: Double
    ) : BottomSheetLayout()
}
