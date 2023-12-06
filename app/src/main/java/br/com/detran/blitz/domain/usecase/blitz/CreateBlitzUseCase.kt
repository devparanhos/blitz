package br.com.detran.blitz.domain.usecase.blitz

import br.com.detran.blitz.domain.mapper.toDomain
import br.com.detran.blitz.domain.repository.blitz.BlitzRepository
import br.com.detran.blitz.presentation.model.blitz.Blitz

class CreateBlitzUseCase(
    private val repository: BlitzRepository
) {
    suspend fun create(blitz: Blitz) {
        repository.createBlitz(blitz.toDomain())
    }
}