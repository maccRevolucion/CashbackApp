package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoadData(
    @SerializedName("product_id")
    val idProduct: Int,
    @SerializedName("quantity")
    val quantity: Int
)

data class ApiResponseLoadItems(
    val success: Boolean,
    val data: PostLoadItemsData?,
    val error: ApiError?
)

data class PostLoadItemsData(
    val idProduct: Int
)