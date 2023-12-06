package br.com.detran.blitz.di.base

import android.app.Application
import br.com.detran.blitz.di.local.databaseModule
import br.com.detran.blitz.di.repository.repositoryModule
import br.com.detran.blitz.di.usecase.usecaseModule
import br.com.detran.blitz.di.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    viewModelModule,
                    databaseModule,
                    usecaseModule,
                    repositoryModule
                )
            )
        }
    }
}