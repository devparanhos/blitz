package br.com.detran.blitz.di.repository

import br.com.detran.blitz.data.local.repository.blitz.BlitzRepositoryImpl
import br.com.detran.blitz.domain.repository.blitz.BlitzRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<BlitzRepository> {
        BlitzRepositoryImpl(blitzDao = get())
    }
}