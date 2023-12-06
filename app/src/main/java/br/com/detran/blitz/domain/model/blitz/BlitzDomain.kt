package br.com.detran.blitz.domain.model.blitz

import br.com.detran.blitz.core.enums.status.Status

data class BlitzDomain(
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String,
    val time: String,
    val status: Status
)
