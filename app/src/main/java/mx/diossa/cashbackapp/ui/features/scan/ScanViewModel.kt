package mx.diossa.cashbackapp.ui.features.scan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    data class ScanUiState(
        val isScanning: Boolean = false,
        val scannedText: String? = null,
        val errorMessage: String? = null,
        val hasCameraPermission: Boolean = false
    )

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    fun checkCameraPermission(context: Context): Boolean {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        _uiState.value = _uiState.value.copy(hasCameraPermission = hasPermission)
        return hasPermission
    }

    fun requestCameraPermission() {
        _uiState.value = _uiState.value.copy(isScanning = true)
    }

    fun onPermissionResult(granted: Boolean) {
        _uiState.value = _uiState.value.copy(
            hasCameraPermission = granted,
            isScanning = granted,
            errorMessage = if (!granted) "Permiso de cámara denegado" else null
        )
    }

    fun processScannedCode(code: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(scannedText = code)
            // Decidir la acción basada en el código
            if (code == "ventas") {
                // Navegación a SalesScreen sería manejada por ScanScreen
            } else {
                _uiState.value = _uiState.value.copy(errorMessage = "QR no válido, escanea de nuevo")
            }
        }
    }

    fun resetState() {
        _uiState.value = _uiState.value.copy(
            scannedText = null,
            errorMessage = null
        )
    }

}