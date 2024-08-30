package br.com.detran.blitz.presentation.feature.fine.state

import android.location.Address
import br.com.detran.blitz.presentation.model.blitz.Blitz

data class FineUiState(
    val loading: Boolean = true,
    val myLocation: Address? = null,
    val nearBlitz: Blitz? = null
)
