package br.com.detran.blitz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.detran.blitz.data.local.entity.BlitzEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlitzDao {
    @Insert
    suspend fun insert(blitz: BlitzEntity)

    @Query("SELECT * FROM blitz")
    suspend fun getBlitz() : List<BlitzEntity>
}