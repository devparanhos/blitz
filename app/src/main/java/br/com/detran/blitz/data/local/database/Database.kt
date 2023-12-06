package br.com.detran.blitz.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.detran.blitz.data.local.dao.BlitzDao
import br.com.detran.blitz.data.local.entity.BlitzEntity

@Database(
    entities = [
        BlitzEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val blitzDao: BlitzDao
}