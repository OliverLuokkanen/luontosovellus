package com.example.luontopeli.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    val locationUpdates: Flow<Location> = callbackFlow {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                trySend(location)
            }
            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000L,
                5f,
                listener
            )
        } catch (e: SecurityException) {
            close(e)
        }
        awaitClose { locationManager.removeUpdates(listener) }
    }
}
