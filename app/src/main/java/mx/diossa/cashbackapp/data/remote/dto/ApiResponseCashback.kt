package mx.diossa.cashbackapp.data.remote.dto

data class ApiResponseCashback(
    val success: Boolean,
    val data: CashbackDetail?,
    val error: String?
)

data class CashbackDetail(
    val idCashback: Int,
    val idRoute: Int,
    val nameRoute: String,
    val idEmployee: Int,
    val nameEmployee: String,
    val cashbackDate: String,
    val cashbackValue: Int,
    val objectiveType: String,
    val status: String
)