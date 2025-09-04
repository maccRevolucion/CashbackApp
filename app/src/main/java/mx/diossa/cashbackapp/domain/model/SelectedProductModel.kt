package mx.diossa.cashbackapp.domain.model

import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmViewModel

data class SelectedProduct(
    val name: String,
    val price: Double,
    val quantity: Int
)

data class UiStateSelectedProduct(
    val selectedProducts: List<SelectedProduct> = emptyList(),
    val total: Double = 0.0,
    val date: String = ""
)

data class UiStateConfirm(
    val confirmationData: CashbackDetail? = null,
    val selectedProducts: List<ConfirmViewModel.UiSelectedProduct> = emptyList(),
    val total: Double = 0.0,
    val isPrinterConnected: Boolean = false
)