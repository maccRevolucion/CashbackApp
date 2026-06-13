package mx.diossa.cashbackapp.data.repository

import android.util.Log
import com.google.gson.Gson
import mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
import mx.diossa.cashbackapp.data.remote.dto.ApiResponse
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.data.remote.dto.EmployeeCarryData
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.LoadData
import mx.diossa.cashbackapp.data.remote.dto.LoadItemDetails
import mx.diossa.cashbackapp.data.remote.dto.UpdateStatusRequest
import retrofit2.HttpException
import javax.inject.Inject

class CashbackRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getCashbackDetails(id: Int): Result<CashbackDetail> {
        return try {
            val response = remoteDataSource.getCashbackDetails(id)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.error?.detail ?: "No se encontraron datos para el ID: $id"))
            }
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()
            val parsed = try {
                Gson().fromJson(errorJson, ApiResponse::class.java)
            } catch (_: Exception) {
                null
            }
            val message = parsed?.error?.detail ?: "Error desconocido"
            Result.failure(Exception(message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCashbackStatus(idCashback: Int): Result<Unit> {
        return try {
            Log.d("CASHBACK_REPOSITOTY", "Actualizando cashback $idCashback a COMPLETED")
            val request = UpdateStatusRequest(status = "COMPLETED")
            val response = remoteDataSource.updateStatus(request)
            if (response.success) {
                Log.d("CASHBACK_REPOSITOTY", "Estado actualizado correctamente")
                Result.success(Unit)
            } else {
                Log.e("CASHBACK_REPOSITOTY", "Error al actualizar estado: ${response.error}")
                Result.failure(Exception(response.error?.detail ?: "No se pudo actualizar el estatus del ID: $idCashback"))
            }
        } catch (e: Exception) {
            Log.e("CASHBACK_REPOSITOTY", "Excepción en updateCashbackStatus", e)
            Result.failure(e)
        }
    }

    suspend fun postExchangeItems(items: List<ItemData>): Result<Unit> {
        return try {
            Log.d("CASHBACK_REPOSITOTY", "POST /liquidation/items $items")

            val liquidationResponse = remoteDataSource.postItems(items)
            if (liquidationResponse.success) {
                Log.d("CASHBACK_REPOSITOTY", "POST de items exitoso.")
                Result.success(Unit)
            } else {
                Log.e("CASHBACK_REPOSITOTY", "Error en POST de items: ${liquidationResponse.error}")
                Result.failure(Exception(liquidationResponse.error?.detail ?: "Error en el POST de items"))
            }
        } catch (e: Exception) {
            Log.e("CASHBACK_REPOSITOTY", "Excepción en postExchangeItems - liquidacion", e)
            Result.failure(e)
        }
    }

    suspend fun postExchangeLoadItems(items: List<LoadData>): Result<Unit> {
        return try {
            Log.d("CASHBACK_REPOSITOTY", "POST /load con $items")

            val loadItemsResponse = remoteDataSource.postLoadItems(items)
            if (loadItemsResponse.success) {
                Log.d("CASHBACK_REPOSITOTY", "POST de items exitoso.")
                Result.success(Unit)
            } else {
                Log.e("CASHBACK_REPOSITOTY", "Error en POST de items: ${loadItemsResponse.error}")
                Result.failure(Exception(loadItemsResponse.error?.detail ?: "Error en el POST de items"))
            }
        } catch (e: Exception) {
            Log.e("CASHBACK_REPOSITOTY", "Excepción en postExchangeItems - Carga", e)
            Result.failure(e)
        }
    }

    suspend fun postCashbackItemDetails(idCashback: Int, items: List<LoadItemDetails>): Result<Unit>{
        return try {
            Log.d("CASHBACK_REPOSITOTY", "POST /details con $items")

            val loadItemsResponse = remoteDataSource.postItemDetails(idCashback, items)
            if (loadItemsResponse.success) {
                Log.d("CASHBACK_REPOSITOTY", "POST de items details exitoso.")
                Result.success(Unit)
            } else {
                Log.e("CASHBACK_REPOSITOTY", "Error en POST de items details: ${loadItemsResponse.error}")
                Result.failure(Exception(loadItemsResponse.error?.detail ?: "Error en el POST de items"))
            }
        } catch (e: Exception) {
            Log.e("CASHBACK_REPOSITOTY", "Excepción en postExchangeItems - detalle", e)
            Result.failure(e)
        }
    }

    suspend fun postCarryData(employeeId: Int, quantity: Int): Result<EmployeeCarryData> {
        return try {
            val response = remoteDataSource.postCarry(EmployeeCarryData(employeeId, quantity))
            if (response.success && response.data != null) {
                val carryData = response.data
                val result = EmployeeCarryData(
                    employeeId = carryData.employeeId,
                    quantity = carryData.quantity
                )
                Result.success(result)
            } else {
                Result.failure(Exception(response.error?.detail ?: "Respuesta de API no exitosa o sin datos"))
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val apiResponse = try {
                Gson().fromJson(errorBody, ApiResponse::class.java)
            } catch (_: Exception) {
                null
            }
            val message = apiResponse?.error?.detail ?: "Error desconocido en el servidor"
            Result.failure(Exception(message))

        } catch (e: Exception) {
            Log.e("CASHBACK_REPOSITOTY", "Post Carry - falló", e)
            Result.failure(e)
        }
    }
}