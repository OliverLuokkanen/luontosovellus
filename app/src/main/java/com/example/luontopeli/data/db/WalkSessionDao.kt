package com.example.luontopeli.data.db

import androidx.room.*
import com.example.luontopeli.data.model.WalkSession
import kotlinx.coroutines.flow.Flow

@Dao
interface WalkSessionDao {
    @Query("SELECT * FROM walk_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<WalkSession>>

    @Query("SELECT * FROM walk_sessions WHERE isActive = 1 LIMIT 1")
    fun getActiveSession(): Flow<WalkSession?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WalkSession): Long

    @Update
    suspend fun updateSession(session: WalkSession)

    @Delete
    suspend fun deleteSession(session: WalkSession)

    @Query("SELECT * FROM walk_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): WalkSession?
}
