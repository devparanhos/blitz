package br.com.detran.blitz.data.local.mapper

import br.com.detran.blitz.core.enums.status.Status
import br.com.detran.blitz.data.local.entity.BlitzEntity
import br.com.detran.blitz.domain.model.blitz.BlitzDomain

fun BlitzEntity.toDomain() : BlitzDomain {
    return BlitzDomain(
        id = this.id,
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        date = this.date,
        time = this.time,
        status = Status.valueOf(this.status)
    )
}

fun BlitzDomain.toEntity() : BlitzEntity {
    return BlitzEntity(
        id = this.id,
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        date = this.date,
        time = this.time,
        status = this.status.name
    )
}