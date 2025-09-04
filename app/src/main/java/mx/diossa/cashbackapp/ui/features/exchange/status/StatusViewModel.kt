package mx.diossa.cashbackapp.ui.features.exchange.status

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.domain.ticket.RedemptionItem
import mx.diossa.cashbackapp.domain.ticket.TicketGenerator
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmViewModel
import java.time.LocalDateTime
import javax.inject.Inject

data class StatusUiState(
    val isAvailable: Boolean = false,
    val isPrinterConnected: Boolean = false,
    val idTicket: Int? = null,
    val balance: Int? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val confirmationData: CashbackDetail? = null,
    val selectedProducts: List<ConfirmViewModel.UiSelectedProduct> = emptyList()
)
@HiltViewModel
class StatusViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val printerConnection: PrinterConnection,
    private val ticketGenerator: TicketGenerator
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatusUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val isAvailable = savedStateHandle.get<Boolean>("isAvailable") ?: false
        val isConnected = savedStateHandle.get<Boolean>("isConnected") ?: false

        val ticketJson = savedStateHandle.get<String>("ticket")
        val productsJson = savedStateHandle.get<String>("products")

        val ticket = ticketJson?.let { Gson().fromJson(it, CashbackDetail::class.java) }
        val selectedProducts = productsJson?.let {
            Gson().fromJson(it, Array<ConfirmViewModel.UiSelectedProduct>::class.java).toList()
        } ?: emptyList()

        _uiState.value = StatusUiState(
            isAvailable = isAvailable,
            isPrinterConnected = isConnected,
            idTicket = ticket?.idCashback,
            balance = ticket?.cashbackValue,
            date = LocalDateTime.now(),
            selectedProducts = selectedProducts,
            confirmationData = ticket
        )
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

        val ticketString = ticketGenerator.buildRedemptionTicket(
            items = items,
            cashback = state.balance?.toDouble() ?: 0.0,
            subtotal = items.sumOf { it.quantity * it.price },
            vendor = "",
            total = 0.0
        )

        if (state.isPrinterConnected) {
            printerConnection.sendMessage(ticketString)
        } else {
            android.util.Log.e("StatusVM", "No hay impresora conectada")
        }
    }
}
