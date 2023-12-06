package br.com.detran.blitz.di.usecase

import br.com.detran.blitz.domain.usecase.blitz.CreateBlitzUseCase
import br.com.detran.blitz.domain.usecase.blitz.GetBlitzUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory {
        GetBlitzUseCase(repository = get())
    }

    factory {
        CreateBlitzUseCase(repository = get())
    }
}