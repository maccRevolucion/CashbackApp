package mx.diossa.cashbackapp.ui.features.exchange

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmViewModel
import mx.diossa.cashbackapp.ui.features.exchange.status.StatusUiState
import java.time.LocalDateTime
import javax.inject.Inject

data class ExchangeState(
    val ticketDetails: CashbackDetail? = null,
    val selectedProducts: Map<Product, Int> = emptyMap()
)

@HiltViewModel
class ExchangeViewModel @Inject constructor() : ViewModel() {
    private val _exchangeState = MutableStateFlow(ExchangeState())
    val exchangeState = _exchangeState.asStateFlow()

    private val _statusState = MutableStateFlow(StatusUiState())
    val statusState: StateFlow<StatusUiState> = _statusState

    fun setTicketDetails(details: CashbackDetail) {
        _exchangeState.update { it.copy(ticketDetails = details) }
    }

    fun setSelectedProducts(products: Map<Product, Int>) {
        _exchangeState.update { it.copy(selectedProducts = products) }
    }

    fun setStatusData(
        isAvailable: Boolean,
        isConnected: Boolean,
        ticket: CashbackDetail?,
        products: List<ConfirmViewModel.UiSelectedProduct>,
        unavailableProducts: List<Product> = emptyList()
    ) {
        _statusState.value = StatusUiState(
            isAvailable = isAvailable,
            isPrinterConnected = isConnected,
            idTicket = ticket?.idCashback,
            balance = ticket?.cashbackValue,
            date = LocalDateTime.now(),
            confirmationData = ticket,
            selectedProducts = products,
            unavailableProducts = unavailableProducts
        )
    }
}