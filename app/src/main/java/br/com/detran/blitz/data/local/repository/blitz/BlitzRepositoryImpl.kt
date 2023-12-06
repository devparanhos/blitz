package br.com.detran.blitz.data.local.repository.blitz

import br.com.detran.blitz.data.local.dao.BlitzDao
import br.com.detran.blitz.data.local.mapper.toDomain
import br.com.detran.blitz.data.local.mapper.toEntity
import br.com.detran.blitz.domain.model.blitz.BlitzDomain
import br.com.detran.blitz.domain.repository.blitz.BlitzRepository

class BlitzRepositoryImpl(
    private val blitzDao: BlitzDao
) : BlitzRepository {
    override suspend fun getBlitz(): List<BlitzDomain> {
        return blitzDao.getBlitz().map { it.toDomain() }
    }

    override suspend fun createBlitz(blitz: BlitzDomain) {
        blitzDao.insert(blitz.toEntity())
    }
}