package br.com.detran.blitz.domain.repository.blitz

import br.com.detran.blitz.domain.model.blitz.BlitzDomain


interface BlitzRepository {
    suspend fun getBlitz() : List<BlitzDomain>
    suspend fun createBlitz(blitz: BlitzDomain)
}