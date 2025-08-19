package mx.diossa.cashbackapp.ui.features.printer

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.domain.model.UiStatePrinter
import javax.inject.Inject

@HiltViewModel
class PrinterViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val _uiState = MutableStateFlow(UiStatePrinter())
    val uiState: StateFlow<UiStatePrinter> = _uiState.asStateFlow()

    init {
        loadSavedMac()
        checkConnection()
    }

    private fun loadSavedMac() {
        val savedMac = sharedPreferences.getString("macPrinter", "") ?: ""
        _uiState.update { it.copy(macAddress = savedMac) }
    }

    fun onMacChanged(newMac: String) {
        _uiState.update { it.copy(macAddress = newMac) }
    }

    fun onNameChanged(newName: String) {
        _uiState.update { it.copy(printerName = newName) }
    }
    fun saveConfig(printerName: String, mac: String) {
        sharedPreferences.edit().putString("macPrinter", mac).apply()
        connectToPrinter(printerName, mac)
    }

    private fun connectToPrinter(printerName: String, mac: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val connection = PrinterConnection.getInstance(context)
                connection?.connectPrinter()
                val isConnected = connection?.isConnectionLost()?.not() ?: false

                if (isConnected) {
                    _uiState.update {
                        it.copy(
                            isConnected = true,
                            isLoading = false,
                            printerName = printerName
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isConnected = false,
                            isLoading = false,
                            printerName = "---"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message, printerName = "---")
                }
            }
        }
    }

    fun checkConnection() {
        val connection = PrinterConnection.getInstance(context)
        val isConnected = connection?.isConnectionLost() ?: false
        _uiState.update { it.copy(isConnected = isConnected) }
    }

}