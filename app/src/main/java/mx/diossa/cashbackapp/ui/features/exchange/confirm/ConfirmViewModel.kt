package mx.diossa.cashbackapp.ui.features.exchange.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.domain.model.UiStateConfirm
import mx.diossa.cashbackapp.domain.ticket.RedemptionItem
import mx.diossa.cashbackapp.domain.ticket.TicketGenerator
import mx.diossa.cashbackapp.domain.usecases.ExchangeResult
import mx.diossa.cashbackapp.domain.usecases.ProcessExchangeUseCase
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val printerConnection: PrinterConnection,
    private val processExchangeUseCase: ProcessExchangeUseCase,
    private val ticketGenerator: TicketGenerator
): ViewModel(){
    private val _uiState = MutableStateFlow(UiStateConfirm())
    val uiState: StateFlow<UiStateConfirm> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<ExchangeResult>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun loadData(ticket: CashbackDetail?, selectedProductsMap: Map<Product, Int>) {
        val selectedProducts = selectedProductsMap.map { (product, quantity) ->
            UiSelectedProduct(product, quantity)
        }

        val total = selectedProducts.sumOf { it.product.price * it.quantity }
        val isConnected = printerConnection.isConnected()

        _uiState.value = UiStateConfirm(
            confirmationData = ticket,
            selectedProducts = selectedProducts,
            total = total,
            isPrinterConnected = isConnected
        )
    }
    fun onConfirmAndProcess(ticket: CashbackDetail, selectedProducts: Map<Product, Int>) {
        viewModelScope.launch {
            android.util.Log.d("ConfirmVM", "Procesando ticket: $ticket con productos: $selectedProducts")

            val result = processExchangeUseCase(ticket, selectedProducts)
            android.util.Log.d("ConfirmVM", "Resultado del UseCase: $result")

            if (result is ExchangeResult.Success) {
                val ticketString = formatTicketForPrinting()
                android.util.Log.d("ConfirmVM", "Ticket generado:\n$ticketString")
                printerConnection.sendMessage(ticketString)
            }

            _navigationEvent.emit(result)
        }
    }


    private fun formatTicketForPrinting(): String {
        val state = _uiState.value
        val ticket = state.confirmationData ?: return "ERROR: TICKET DATA MISSING"

        val items = state.selectedProducts.map {
            RedemptionItem(
                name = it.product.name,
                quantity = it.quantity,
                price = it.product.price
            )
        }

        return ticketGenerator.buildRedemptionTicket(
            items = items,
            cashback =(ticket.cashbackValue.toDouble()),
            subtotal = state.total,
            vendor = ticket.nameEmployee,
            total = state.total
        )
    }
    data class UiSelectedProduct(
        val product: Product,
        val quantity: Int
    )
}
