package com.example.luontopeli.data.repository

import com.example.luontopeli.firebase.FirestoreManager
import com.example.luontopeli.firebase.StorageManager
import com.example.luontopeli.data.model.NatureSpot
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles syncing local NatureSpots to Firebase (Firestore + Storage).
 * Follows an offline-first approach: data is always stored in Room first,
 * then a background sync attempts to push to the cloud.
 */
@Singleton
class SyncManager @Inject constructor(
    private val spotRepository: NatureSpotRepository,
    private val firestoreManager: FirestoreManager,
    private val storageManager: StorageManager
) {

    /**
     * Attempts to sync a single NatureSpot to Firebase.
     * Uploads the image to Storage first, then saves metadata to Firestore.
     * Marks the spot as synced in Room if both operations succeed.
     */
    suspend fun syncSpot(spot: NatureSpot) {
        if (spot.synced) return
        try {
            val imageUrl = spot.imageLocalPath?.let { localPath ->
                storageManager.uploadImage(localPath, spot.id)
            }
            val spotToSync = if (imageUrl != null) spot.copy(imageFirebaseUrl = imageUrl) else spot
            val saved = firestoreManager.saveSpot(spotToSync)
            if (saved) {
                spotRepository.markSynced(spotToSync)
            }
        } catch (_: Exception) {
            // Sync failed – data remains unsynced in Room and will be retried later.
        }
    }

    /**
     * Attempts to sync all unsynced NatureSpots to Firebase.
     */
    suspend fun syncAllPending() {
        val unsyncedSpots = spotRepository.getUnsyncedSpots()
        unsyncedSpots.forEach { spot -> syncSpot(spot) }
    }
}
