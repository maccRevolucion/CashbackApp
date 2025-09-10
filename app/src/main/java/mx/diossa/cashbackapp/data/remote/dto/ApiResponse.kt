package mx.diossa.cashbackapp.data.remote.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: Any?,
    val error: ApiError?
)

data class ApiError(
    val code: Int,
    val message: String,
    val detail: String
)
