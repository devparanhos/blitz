package br.com.detran.blitz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("blitz")
data class BlitzEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var latitude: Double,
    var longitude: Double,
    var address: String,
    var date: String,
    var time: String,
    var status: String
)
