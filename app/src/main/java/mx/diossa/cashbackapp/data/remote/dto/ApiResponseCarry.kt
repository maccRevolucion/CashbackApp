package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeCarryData(
    @SerializedName("employee_id")
    val employeeId: Int,
    @SerializedName("quantity")
    val quantity: Int
)

data class ApiResponseCarry(
    val success: Boolean,
    val data: EmployeeCarryData?,
    val error: ApiError?
)