package mx.diossa.cashbackapp.data.remote.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: String?
)