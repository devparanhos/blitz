package br.com.detran.blitz.di.viewmodel

import br.com.detran.blitz.presentation.feature.fine.viewmodel.FineViewModel
import br.com.detran.blitz.presentation.feature.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            getBlitzUseCase = get(),
            createBlitzUseCase = get()
        )
    }

    viewModel {
        FineViewModel(
            context = androidContext()
        )
    }
}