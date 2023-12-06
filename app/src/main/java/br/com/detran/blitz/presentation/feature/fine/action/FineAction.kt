package br.com.detran.blitz.presentation.feature.fine.action

import br.com.detran.blitz.presentation.model.blitz.Blitz

sealed class FineAction {
    data class GetLocation(val blitz: List<Blitz>) : FineAction()
}
