package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
)

data class ApiResponseLogin(
    val success: Boolean,
    val data: LoginData,
    val error: ApiError?
)

data class LoginData(
    @SerializedName("employee_id")
    val idEmployee: Int,

    val username: String,

    @SerializedName("employee_name")
    val employeeName: String,

    @SerializedName("cedis_id")
    val idCedis: Int,

    @SerializedName("cedis_name")
    val nameCedis: String,

    @SerializedName("access_token")
    val access: String,

    @SerializedName("refresh_token")
    val refresh: String
)