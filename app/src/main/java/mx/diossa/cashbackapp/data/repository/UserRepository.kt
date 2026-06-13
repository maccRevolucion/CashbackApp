package mx.diossa.cashbackapp.data.repository

import android.util.Log
import mx.diossa.cashbackapp.data.local.datasource.UserLocalDataSource
import mx.diossa.cashbackapp.data.local.entity.UserEntity
import mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
import mx.diossa.cashbackapp.data.remote.dto.ApiError
import mx.diossa.cashbackapp.data.remote.dto.ApiResponse
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLogin
import mx.diossa.cashbackapp.data.remote.dto.LoginData
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: RemoteDataSource
){
    suspend fun login(username: String, password: String): Result<LoginData> {
        return try {
            val response = remoteDataSource.login(LoginRequest(username, password))
            if (response.success) {
                val loginData = response.data
                val result = LoginData(
                    idEmployee = loginData.idEmployee,
                    employeeName = loginData.employeeName,
                    username = loginData.username,
                    nameCedis = loginData.nameCedis,
                    idCedis = loginData.idCedis,
                    access = loginData.access,
                    refresh = loginData.refresh
                )
                Result.success(result)
            } else {
                Result.failure(Exception(response.error?.detail ?: "Respuesta de API no exitosa o sin datos"))
            }

        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val apiResponse = try {
                com.google.gson.Gson().fromJson(errorBody, ApiResponse::class.java)
            } catch (ex: Exception) {
                null
            }
            val message = apiResponse?.error?.detail ?: "Error desconocido en el servidor"
            Result.failure(Exception(message))

        } catch (e: Exception) {
            Log.e("UserRepository", "Login falló", e)
            Result.failure(e)
        }
    }

    suspend fun validateUser(username: String, password: String): LoginRequest {
        localDataSource.initializeUserIfNeeded()
        return localDataSource.validateUser(username, password)
    }
}