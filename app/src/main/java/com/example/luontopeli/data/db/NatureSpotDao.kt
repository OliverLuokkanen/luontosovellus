package com.example.luontopeli.data.db

import androidx.room.*
import com.example.luontopeli.data.model.NatureSpot
import kotlinx.coroutines.flow.Flow

@Dao
interface NatureSpotDao {
    @Query("SELECT * FROM nature_spots ORDER BY timestamp DESC")
    fun getAllSpots(): Flow<List<NatureSpot>>

    @Query("SELECT * FROM nature_spots WHERE synced = 0")
    suspend fun getUnsyncedSpots(): List<NatureSpot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpot(spot: NatureSpot)

    @Update
    suspend fun updateSpot(spot: NatureSpot)

    @Delete
    suspend fun deleteSpot(spot: NatureSpot)

    @Query("SELECT * FROM nature_spots WHERE id = :id")
    suspend fun getSpotById(id: String): NatureSpot?
}
