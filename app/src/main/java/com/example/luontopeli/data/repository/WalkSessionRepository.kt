package com.example.luontopeli.data.repository

import com.example.luontopeli.data.db.WalkSessionDao
import com.example.luontopeli.data.model.WalkSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalkSessionRepository @Inject constructor(
    private val dao: WalkSessionDao
) {
    fun getAllSessions(): Flow<List<WalkSession>> = dao.getAllSessions()
    fun getActiveSession(): Flow<WalkSession?> = dao.getActiveSession()
    suspend fun startSession(): Long {
        val session = WalkSession(startTime = System.currentTimeMillis())
        return dao.insertSession(session)
    }
    suspend fun updateSession(session: WalkSession) = dao.updateSession(session)
    suspend fun endSession(session: WalkSession) {
        dao.updateSession(session.copy(endTime = System.currentTimeMillis(), isActive = false))
    }
}
