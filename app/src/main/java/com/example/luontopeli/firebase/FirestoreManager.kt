package com.example.luontopeli.firebase

import com.example.luontopeli.data.model.NatureSpot
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages Firestore operations. Currently a stub for offline-first architecture.
 * To integrate real Firestore, add Firebase SDK and replace the stub with:
 *   Firebase.firestore.collection("spots").document(spot.id).set(spot.toMap()).await()
 */
@Singleton
class FirestoreManager @Inject constructor() {

    /**
     * Saves a NatureSpot to Firestore.
     * @return true if saved successfully, false otherwise.
     */
    suspend fun saveSpot(spot: NatureSpot): Boolean {
        // Offline stub – always returns false until real Firebase is configured.
        // Replace with actual Firestore call when google-services.json is added.
        return false
    }
}
