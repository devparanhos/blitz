package br.com.detran.blitz.domain.usecase.blitz

import br.com.detran.blitz.domain.mapper.toModel
import br.com.detran.blitz.domain.repository.blitz.BlitzRepository
import br.com.detran.blitz.presentation.model.blitz.Blitz

class GetBlitzUseCase(
    private val repository: BlitzRepository
) {
    suspend fun getBlitz() : List<Blitz> {
        return repository.getBlitz().map { it.toModel() }
    }
}