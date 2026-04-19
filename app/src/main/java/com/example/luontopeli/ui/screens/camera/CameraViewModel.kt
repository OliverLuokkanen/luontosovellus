package com.example.luontopeli.ui.screens.camera

import android.content.Context
import android.location.Location
import android.os.Environment
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.model.NatureSpot
import com.example.luontopeli.data.repository.NatureSpotRepository
import com.example.luontopeli.data.repository.SyncManager
import com.example.luontopeli.firebase.AuthManager
import com.example.luontopeli.location.LocationHelper
import com.example.luontopeli.ml.PlantClassifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

data class CameraUiState(
    val capturedImagePath: String? = null,
    val plantLabel: String? = null,
    val confidence: Float? = null,
    val isAnalyzing: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val savedSuccessfully: Boolean = false
)

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val spotRepository: NatureSpotRepository,
    private val locationHelper: LocationHelper,
    private val plantClassifier: PlantClassifier,
    private val authManager: AuthManager,
    private val syncManager: SyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    var imageCapture: ImageCapture? = null

    private var lastLocation: Location? = null

    init {
        viewModelScope.launch {
            locationHelper.locationUpdates.collect { location ->
                lastLocation = location
            }
        }
    }

    fun onCameraReady(capture: ImageCapture) {
        imageCapture = capture
    }

    fun capturePhoto(context: Context) {
        val capture = imageCapture ?: return
        val photoFile = createImageFile(context) ?: return

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        capture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(isAnalyzing = true, errorMessage = null) }
                        val (label, confidence) = plantClassifier.classify(photoFile)
                        _uiState.update {
                            it.copy(
                                isAnalyzing = false,
                                capturedImagePath = photoFile.absolutePath,
                                plantLabel = label,
                                confidence = confidence
                            )
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    _uiState.update {
                        it.copy(errorMessage = exception.message ?: "Kuvan ottaminen epäonnistui")
                    }
                }
            }
        )
    }

    fun saveSpot() {
        val imagePath = _uiState.value.capturedImagePath ?: return
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val spot = NatureSpot(
                id = UUID.randomUUID().toString(),
                name = _uiState.value.plantLabel ?: "Luontohavainto",
                latitude = lastLocation?.latitude ?: 0.0,
                longitude = lastLocation?.longitude ?: 0.0,
                imageLocalPath = imagePath,
                plantLabel = _uiState.value.plantLabel,
                confidence = _uiState.value.confidence,
                userId = authManager.getUserId(),
                timestamp = System.currentTimeMillis(),
                synced = false
            )
            spotRepository.insertSpot(spot)
            syncManager.syncSpot(spot)
            _uiState.update { it.copy(isSaving = false, savedSuccessfully = true) }
        }
    }

    fun resetCapture() {
        _uiState.update {
            CameraUiState()
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun createImageFile(context: Context): File? {
        return try {
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File(storageDir, "luontopeli_${System.currentTimeMillis()}.jpg")
        } catch (e: Exception) {
            null
        }
    }
}
