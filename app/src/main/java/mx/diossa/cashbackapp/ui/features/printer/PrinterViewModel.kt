package mx.diossa.cashbackapp.ui.features.printer

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import mx.diossa.cashbackapp.core.utils.BluetoothService
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.domain.model.UiStatePrinter
import javax.inject.Inject

@HiltViewModel
class PrinterViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiStatePrinter())
    val uiState: StateFlow<UiStatePrinter> = _uiState.asStateFlow()

    private val printerConnection = PrinterConnection.getInstance(context)

    init {
        observePrinterStatus()
        loadSavedMac()
    }

    private fun loadSavedMac() {
        val savedMac = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            .getString("macPrinter", "") ?: ""
        _uiState.update { it.copy(macAddress = savedMac) }
    }
    private fun observePrinterStatus() {
        printerConnection.printerStatus.onEach { status ->
            _uiState.update {
                it.copy(
                    isConnected = status.state == BluetoothService.STATE_CONNECTED,
                    printerName = status.deviceName,
                    isLoading = status.state == BluetoothService.STATE_CONNECTING
                )
            }
            Log.d("PrinterVM", "Printer status updated: State=${status.state}, Name=${status.deviceName}")
        }.launchIn(viewModelScope)
    }

    fun onMacChanged(newMac: String) {
        _uiState.update { it.copy(macAddress = newMac) }
    }

    fun onNameChanged(newName: String) {
        _uiState.update { it.copy(printerName = newName) }
    }

    fun saveConfig(mac: String) {
        if (!isValidMac(mac)) {
            _uiState.update { it.copy(error = "MAC inválida (formato 00:11:22:33:44:55)") }
            return
        }
        _uiState.update { it.copy(error = null, isLoading = true) }
        printerConnection.updateMacAndConnect(mac)
    }

    private fun isValidMac(mac: String): Boolean {
        return mac.matches(Regex("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")) && mac != "00:00:00:00:00:00"
    }

}