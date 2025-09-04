package mx.diossa.cashbackapp.ui.features.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.domain.usecases.GetCashbackDetailsUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed interface QrValidationState {
    object Idle : QrValidationState
    object Loading : QrValidationState
    data class Success(val details: CashbackDetail, val isValid: Boolean) : QrValidationState
    data class Error(val message: String) : QrValidationState
}

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val getCashbackDetailsUseCase: GetCashbackDetailsUseCase
) : ViewModel() {

    private val _validationState = MutableStateFlow<QrValidationState>(QrValidationState.Idle)
    val validationState = _validationState.asStateFlow()

    fun processScannedCode(idCashback: String) {
        if (_validationState.value is QrValidationState.Loading) return

        viewModelScope.launch {
            _validationState.value = QrValidationState.Loading
            getCashbackDetailsUseCase(idCashback.toInt()).onSuccess { details ->
                val isValid = isCashbackDateToday(details.cashbackDate)
                _validationState.value = QrValidationState.Success(details, isValid)
            }.onFailure { error ->
                _validationState.value = QrValidationState.Error(error.message ?: "ID de Cashback no válido")
            }
        }
    }

    private fun isCashbackDateToday(dateString: String?): Boolean {
        if (dateString == null) return false

        return try {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val cashbackDate = LocalDate.parse(dateString, formatter)
            cashbackDate.isEqual(LocalDate.now())
        } catch (e: Exception) {
            false
        }
    }

    fun resetState() {
        _validationState.value = QrValidationState.Idle
    }
}