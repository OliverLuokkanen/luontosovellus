package com.example.luontopeli.firebase

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages user authentication. Currently provides an anonymous local UUID.
 * To integrate real Firebase Auth, replace the UUID logic with:
 *   Firebase.auth.signInAnonymously().await().user?.uid
 */
@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun getUserId(): String {
        return prefs.getString(KEY_USER_ID, null) ?: run {
            val newId = UUID.randomUUID().toString()
            prefs.edit().putString(KEY_USER_ID, newId).apply()
            newId
        }
    }

    companion object {
        private const val KEY_USER_ID = "userId"
    }
}
