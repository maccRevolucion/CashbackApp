package mx.diossa.cashbackapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiResponseCashback(
    val success: Boolean,
    val data: CashbackDetail?,
    val error: ApiError?
)

data class CashbackDetail(
    @SerializedName("cashback_id")
    val idCashback: Int,

    @SerializedName("route_id")
    val idRoute: Int,

    @SerializedName("route_name")
    val nameRoute: String,

    @SerializedName("employee_id")
    val idEmployee: Int,

    @SerializedName("employee_name")
    val nameEmployee: String,

    @SerializedName("cashback_date")
    val cashbackDate: String,

    @SerializedName("cashback_value")
    val cashbackValue: Int,

    @SerializedName("objective_type")
    val objectiveType: String,

    val status: String
)