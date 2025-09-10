package mx.diossa.cashbackapp.ui.features.exchange.status

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.domain.ticket.RedemptionItem
import mx.diossa.cashbackapp.domain.ticket.TicketGenerator
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import javax.inject.Inject

data class StatusUiState(
    val isAvailable: Boolean = false,
    val isPrinterConnected: Boolean = false,
    val idTicket: Int? = null,
    val balance: Int? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val confirmationData: CashbackDetail? = null,
    val selectedProducts: List<ConfirmViewModel.UiSelectedProduct> = emptyList(),
    val unavailableProducts: List<Product> = emptyList()
)
@HiltViewModel
class StatusViewModel @Inject constructor(
    private val printerConnection: PrinterConnection,
    private val ticketGenerator: TicketGenerator
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatusUiState())
    val uiState = _uiState.asStateFlow()

    fun setData(state: StatusUiState) {
        _uiState.value = state
    }

    fun reprintTicket() {
        val state = _uiState.value
        val items = state.selectedProducts.map {
            RedemptionItem(
                name = it.product.name,
                quantity = it.quantity,
                price = it.product.price
            )
        }

        val df = DecimalFormat("#0.00")

        val ticketString = ticketGenerator.buildRedemptionTicket(
            items = items,
            cashback = df.format(state.balance?.toDouble() ?: 0.0).toDouble(),
            subtotal = df.format(items.sumOf { it.quantity * it.price }).toDouble(),
            vendor = state.confirmationData?.nameEmployee ?: "",
            total = 0.0
        )

        if (state.isPrinterConnected) {
            printerConnection.sendMessage(ticketString)
        } else {
            android.util.Log.e("StatusVM", "No hay impresora conectada")
        }
    }
}

