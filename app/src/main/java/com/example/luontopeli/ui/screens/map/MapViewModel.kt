package com.example.luontopeli.ui.screens.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.location.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

data class MapUiState(
    val currentLocation: Location? = null,
    val routePoints: List<GeoPoint> = emptyList()
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun startLocationUpdates() {
        viewModelScope.launch {
            locationHelper.locationUpdates.collect { location ->
                _uiState.update { state ->
                    val newPoint = GeoPoint(location.latitude, location.longitude)
                    state.copy(
                        currentLocation = location,
                        routePoints = state.routePoints + newPoint
                    )
                }
            }
        }
    }
}
