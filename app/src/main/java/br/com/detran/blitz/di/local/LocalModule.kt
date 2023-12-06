package br.com.detran.blitz.di.local

import androidx.room.Room
import br.com.detran.blitz.data.local.database.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java,
            "blitz.db",
        ).build()
    }

    single {
        get<Database>().blitzDao
    }
}