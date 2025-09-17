package mx.diossa.cashbackapp.data.remote.datasource

import mx.diossa.cashbackapp.data.remote.api.ApiService
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseCashback
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseInventory
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLoadItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLogin
import mx.diossa.cashbackapp.data.remote.dto.ApiResponsePostItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseUpdateStatus
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.LoadData
import mx.diossa.cashbackapp.data.remote.dto.UpdateStatusRequest
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(request: LoginRequest): ApiResponseLogin = apiService.postLogin(request)

    suspend fun getInventory(): ApiResponseInventory = apiService.getInventory()

    suspend fun getCashbackDetails(idCashback: Int): ApiResponseCashback = apiService.getCashbackDetails(idCashback)

    suspend fun updateStatus(idCashback: Int, request: UpdateStatusRequest): ApiResponseUpdateStatus = apiService.updateStatus(idCashback, request)

    suspend fun postItems(request: List<ItemData>): ApiResponsePostItems = apiService.postItems(request)

    suspend fun postLoadItems(request: List<LoadData>): ApiResponseLoadItems = apiService.postLoadItems(request)
}