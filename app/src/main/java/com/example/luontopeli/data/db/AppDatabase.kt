package com.example.luontopeli.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.luontopeli.data.model.NatureSpot
import com.example.luontopeli.data.model.WalkSession

@Database(
    entities = [WalkSession::class, NatureSpot::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkSessionDao(): WalkSessionDao
    abstract fun natureSpotDao(): NatureSpotDao
}
