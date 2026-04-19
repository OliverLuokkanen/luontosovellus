package com.example.luontopeli.data.repository

import com.example.luontopeli.data.db.NatureSpotDao
import com.example.luontopeli.data.model.NatureSpot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NatureSpotRepository @Inject constructor(
    private val dao: NatureSpotDao
) {
    fun getAllSpots(): Flow<List<NatureSpot>> = dao.getAllSpots()
    suspend fun insertSpot(spot: NatureSpot) = dao.insertSpot(spot)
    suspend fun updateSpot(spot: NatureSpot) = dao.updateSpot(spot)
    suspend fun getUnsyncedSpots(): List<NatureSpot> = dao.getUnsyncedSpots()
    suspend fun markSynced(spot: NatureSpot) = dao.updateSpot(spot.copy(synced = true))
}
