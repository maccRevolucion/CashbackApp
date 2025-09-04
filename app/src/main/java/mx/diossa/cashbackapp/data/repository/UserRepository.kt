package mx.diossa.cashbackapp.data.repository

import android.util.Log
import mx.diossa.cashbackapp.data.local.datasource.UserLocalDataSource
import mx.diossa.cashbackapp.data.local.entity.UserEntity
import mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
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
            if (response.success && response.data != null) {
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
                Result.failure(Exception(response.error ?: "Respuesta de API no exitosa o sin datos"))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Login falló", e)
            Result.failure(e)
        }
    }
    suspend fun validateUser(username: String, password: String): UserEntity?{
        localDataSource.initializeUserIfNeeded()
        return localDataSource.validateUser(username, password)
    }
}