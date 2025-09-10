package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import mx.diossa.cashbackapp.domain.model.Product

data class ApiResponseInventory(
    val success: Boolean,
    val data: List<InventoryItem>?,
    val error: ApiError?
)

data class InventoryItem(
    @SerializedName("product_id")
    val idProduct: Int,
    @SerializedName("product_name")
    val nameProduct: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    val quantity: Int
    )

fun InventoryItem.toDomain(): Product {
    return Product(
        id = this.idProduct.toString(),
        name = this.nameProduct,
        price = this.unitPrice,
        quantity = this.quantity
    )
}