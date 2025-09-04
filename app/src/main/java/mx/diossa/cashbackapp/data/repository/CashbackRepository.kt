package mx.diossa.cashbackapp.data.repository

import android.util.Log
import mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.UpdateStatusRequest
import javax.inject.Inject

class CashbackRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getCashbackDetails(id: Int): Result<CashbackDetail>{
        return try {
            val response = remoteDataSource.getCashbackDetails(id)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.error ?: "No se encontraron datos para el ID: $id"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun updateCashbackStatus(idCashback: Int): Result<Unit> {
        return try {
            Log.d("CashbackRepo", "Actualizando cashback $idCashback a COMPLETED")
            val request = UpdateStatusRequest(status = "COMPLETED")
            val response = remoteDataSource.updateStatus(idCashback, request)
            if (response.success) {
                Log.d("CashbackRepo", "Estado actualizado correctamente")
                Result.success(Unit)
            } else {
                Log.e("CashbackRepo", "Error al actualizar estado: ${response.error}")
                Result.failure(Exception(response.error ?: "Error al actualizar estado"))
            }
        } catch (e: Exception) {
            Log.e("CashbackRepo", "Excepción en updateCashbackStatus", e)
            Result.failure(e)
        }
    }

    suspend fun postExchangeItems(items: List<ItemData>): Result<Unit> {
        return try {
            Log.d("CashbackRepo", "POST /liquidation/items y /load con $items")

            val liquidationResponse = remoteDataSource.postItems(items)
            if (liquidationResponse.success) {
                Log.d("CashbackRepo", "POST de items exitoso.")
                Result.success(Unit)
            } else {
                Log.e("CashbackRepo", "Error en POST de items: ${liquidationResponse.error}")
                Result.failure(Exception(liquidationResponse.error ?: "Error en el POST de items"))
            }
        } catch (e: Exception) {
            Log.e("CashbackRepo", "Excepción en postExchangeItems", e)
            Result.failure(e)
        }
    }




}