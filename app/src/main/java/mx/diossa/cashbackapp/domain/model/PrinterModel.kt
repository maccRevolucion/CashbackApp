package mx.diossa.cashbackapp.domain.model

data class UiStatePrinter(
    val macAddress: String = "",
    val printerName: String = "---",
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)