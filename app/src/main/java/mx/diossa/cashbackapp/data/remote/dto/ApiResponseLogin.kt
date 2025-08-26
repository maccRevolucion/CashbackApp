package mx.diossa.cashbackapp.data.remote.dto

data class LoginRequest(
    val user: String,
    val pass: String
)

data class ApiResponseLogin(
    val success: Boolean,
    val data: List<LoginData>?,
    val error: String?
)

data class LoginData(
    val idEmployee: Int,
    val username: String,
    val nameEmployee: String,
    val idCedis: Int,
    val nameCedis: String,
    val access: String,
    val refresh: String?
)