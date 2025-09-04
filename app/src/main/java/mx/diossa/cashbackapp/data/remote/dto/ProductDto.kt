package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    val quantity: Int
)