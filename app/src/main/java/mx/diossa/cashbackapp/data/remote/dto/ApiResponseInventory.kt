package mx.diossa.cashbackapp.data.remote.dto

data class ApiResponseInventory(
    val success: Boolean,
    val data: List<InventoryItem>?,
    val error: String?
)

data class InventoryItem(
    val idProduct: Int,
    val nameProduct: String,
    val quantity: Int,
    val unitPrice: Double
)