package br.com.detran.blitz.presentation.model.blitz

import br.com.detran.blitz.core.enums.status.Status
import br.com.detran.blitz.presentation.model.offender.Offender

data class Blitz(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String,
    val time: String,
    val status: Status = Status.PENDING,
    val offender: List<Offender>? = null
)
