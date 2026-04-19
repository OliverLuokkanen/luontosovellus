package com.example.luontopeli.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "walk_sessions")
data class WalkSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long? = null,
    val stepCount: Int = 0,
    val distanceMeters: Float = 0f,
    val spotsFound: Int = 0,
    val isActive: Boolean = true
)
