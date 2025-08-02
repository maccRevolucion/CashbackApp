package mx.diossa.cashbackapp.presentation.exchange.ticketCheck

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCheckViewModel @Inject constructor(): ViewModel() {

    data class OrderItem (
        var productName: String,
        var quantity: Int,
        val unitPrice: Double = 15.2
    ){
        val subTotal: Double get() = quantity * unitPrice
    }

    data class SalesUiState(
        val productName: String = "",
        val quantityText: String = "",
        val items: List<OrderItem> = emptyList(),
        val total: Double = 0.0
        )

    sealed class NavigationEvent {
            data object NavigateBack : NavigationEvent()
        }

        private val _uiState = MutableStateFlow(SalesUiState())
        val uiState = _uiState.asStateFlow()

        private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
        val navigationEvent = _navigationEvent.asStateFlow()

     fun updateProductName(name: String) {
            _uiState.value = _uiState.value.copy(productName = name)
        }

        fun updateQuantity(quantity: String) {
            _uiState.value = _uiState.value.copy(quantityText = quantity)
        }

        fun addOrderItem() {
            val productName = _uiState.value.productName
            val quantity = _uiState.value.quantityText.toIntOrNull()
            if (productName.isNotBlank() && quantity != null && quantity > 0) {
                val newItem = OrderItem(productName, quantity)
                val updatedItems = _uiState.value.items + newItem
                _uiState.value = _uiState.value.copy(
                    items = updatedItems,
                    total = updatedItems.sumOf { it.subTotal },
                    productName = "",
                    quantityText = ""
                )
                FocusInteraction.Focus()
            }
        }

        fun removeOrderItem(index: Int) {
            val updatedItems = _uiState.value.items.toMutableList().apply { removeAt(index) }
            _uiState.value = _uiState.value.copy(
                items = updatedItems,
                total = updatedItems.sumOf { it.subTotal }
            )
        }

        fun navigateBack() {
            viewModelScope.launch {
                _navigationEvent.value = NavigationEvent.NavigateBack
            }
        }
}
