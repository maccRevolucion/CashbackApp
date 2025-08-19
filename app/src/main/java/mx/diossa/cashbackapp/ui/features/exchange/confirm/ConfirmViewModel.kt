package mx.diossa.cashbackapp.ui.features.exchange.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.diossa.cashbackapp.domain.model.SelectedProduct
import mx.diossa.cashbackapp.domain.model.UiStateSelectedProduct
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _uiState = MutableStateFlow(UiStateSelectedProduct())
    val uiState: StateFlow<UiStateSelectedProduct> = _uiState.asStateFlow()

    init {
        val selectedString = savedStateHandle.get<String>("selected") ?: ""
        val selectedProducts = if (selectedString.isNotEmpty()) {
            selectedString.split(",").mapNotNull { part ->
                val parts = part.split(":")
                if (parts.size == 3) {
                    try {
                        SelectedProduct(parts[0], parts[1].toDouble(), parts[2].toInt())
                    } catch (e: Exception) {
                        null
                    }
                } else null
            }
        } else emptyList()

        val total = selectedProducts.sumOf { it.price * it.quantity }

        val date = SimpleDateFormat("dd/MM/yyyy").format(Date())

        _uiState.value = UiStateSelectedProduct(selectedProducts, total, date)
    }
}