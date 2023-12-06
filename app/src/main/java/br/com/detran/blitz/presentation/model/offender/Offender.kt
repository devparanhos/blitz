package br.com.detran.blitz.presentation.model.offender

import br.com.detran.blitz.presentation.model.vehicle.Vehicle

data class Offender(
    val name: String,
    val cnh: Long,
    val vehicle: Vehicle
)
