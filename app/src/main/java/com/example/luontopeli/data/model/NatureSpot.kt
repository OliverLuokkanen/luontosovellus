package com.example.luontopeli.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nature_spots")
data class NatureSpot(
    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val imageLocalPath: String? = null,
    val imageFirebaseUrl: String? = null,
    val plantLabel: String? = null,
    val confidence: Float? = null,
    val userId: String,
    val timestamp: Long,
    val synced: Boolean = false
)
