package mx.diossa.cashbackapp.data.remote.dto

data class UpdateStatusRequest(
    val status: String
)

data class ApiResponseUpdateStatus(
    val success: Boolean,
    val data: Any?,
    val error: ApiError?
)