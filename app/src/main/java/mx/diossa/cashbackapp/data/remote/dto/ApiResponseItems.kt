package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ItemData(
    @SerializedName("product_id")
    val idProduct: Int,
    @SerializedName("quantity")
    val quantity: Int
)

data class ApiResponsePostItems(
    val success: Boolean,
    val data: PostItemsData?,
    val error: String?
)

data class PostItemsData(
    val idProduct: Int
    // Otros
)