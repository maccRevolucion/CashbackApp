package mx.diossa.cashbackapp.data.remote.api

import mx.diossa.cashbackapp.data.remote.dto.ApiResponseCashback
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseInventory
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLoadItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLogin
import mx.diossa.cashbackapp.data.remote.dto.ApiResponsePostItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseUpdateStatus
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.LoadData
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest
import mx.diossa.cashbackapp.data.remote.dto.UpdateStatusRequest
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun postLogin( @Body request: LoginRequest): ApiResponseLogin

    @GET("warehouse/inventory")
    suspend fun getInventory(): ApiResponseInventory

    @GET("cashback/{id_cashback}")
    suspend fun getCashbackDetails(@Path("id_cashback") idCashback: Int): ApiResponseCashback

    @PUT("cashback/{id_cashback}/status")
    suspend fun updateStatus(@Path("id_cashback") idCashback: Int, @Body request: UpdateStatusRequest): ApiResponseUpdateStatus

    @POST("liquidation/items")
    suspend fun postItems(@Body request: List<ItemData>): ApiResponsePostItems

    @POST("load")
    suspend fun postLoadItems(@Body request: List<LoadData>): ApiResponseLoadItems

}