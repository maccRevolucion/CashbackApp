package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoadItemDetails(
    @SerializedName("product_id")
    val idProduct: Int,
    @SerializedName("quantity")
    val quantity: Int
)

data class ApiResponseCashbackDetails(
    val success: Boolean,
    val data: PostItemsDetails?,
    val error: ApiError?
)

data class PostItemsDetails(
    val idProduct: Int
)