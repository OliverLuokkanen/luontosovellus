package com.example.luontopeli.ui.screens.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.model.NatureSpot
import com.example.luontopeli.data.repository.NatureSpotRepository
import com.example.luontopeli.data.repository.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val spotRepository: NatureSpotRepository,
    private val syncManager: SyncManager
) : ViewModel() {

    val spots: StateFlow<List<NatureSpot>> = spotRepository.getAllSpots()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        syncPendingSpots()
    }

    fun syncPendingSpots() {
        viewModelScope.launch {
            syncManager.syncAllPending()
        }
    }
}
