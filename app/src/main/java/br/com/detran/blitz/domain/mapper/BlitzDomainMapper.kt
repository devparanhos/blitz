package br.com.detran.blitz.domain.mapper

import br.com.detran.blitz.domain.model.blitz.BlitzDomain
import br.com.detran.blitz.presentation.model.blitz.Blitz

fun BlitzDomain.toModel() : Blitz {
    return Blitz(
        address = this.address,
        latitude = this.latitude,
        longitude = this.longitude,
        date = this.date,
        time = this.time,
        status = this.status,
        offender = null
    )
}

fun Blitz.toDomain() : BlitzDomain {
    return BlitzDomain(
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        date = this.date,
        time = this.time,
        status = this.status
    )
}