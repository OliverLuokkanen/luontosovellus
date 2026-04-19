package com.example.luontopeli.firebase

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages Firebase Storage operations. Currently a stub for offline-first architecture.
 * To integrate real Firebase Storage, add Firebase SDK and replace the stub with:
 *   Firebase.storage.reference.child("images/$spotId.jpg").putFile(Uri.fromFile(imageFile)).await()
 *   and then call .downloadUrl.await().toString() to get the public URL.
 */
@Singleton
class StorageManager @Inject constructor() {

    /**
     * Uploads a local image file to Firebase Storage.
     * @param localPath Absolute path to the local image file.
     * @param spotId    The NatureSpot ID used as the remote file name.
     * @return The public download URL, or null if upload failed.
     */
    suspend fun uploadImage(localPath: String, spotId: String): String? {
        // Offline stub – always returns null until real Firebase is configured.
        // Replace with actual Storage upload when google-services.json is added.
        return null
    }
}
