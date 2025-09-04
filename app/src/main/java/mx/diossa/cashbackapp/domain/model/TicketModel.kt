package mx.diossa.cashbackapp.domain.model

import com.google.gson.annotations.SerializedName
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import java.time.LocalDateTime

data class Ticket(
    @SerializedName("cashback_id")
    val idCashback: String,

    @SerializedName("route_id")
    val idRoute: String,

    @SerializedName("route_name")
    val routeName: String,

    @SerializedName("employee_id")
    val idEmployee: Int,

    @SerializedName("employee_name")
    val employeeName: String,

    @SerializedName("cashback_date")
    val date: String,

    @SerializedName("cashback_value")
    val cashbackValue: Int,

    @SerializedName("objective_type")
    val objectiveType: String,

    val status: String
)

data class UiStateTicket(
    val query: String = "",
    val isAscending: Boolean = false,
    val filteredTickets: List<TicketEntity> = emptyList()
)

data class TicketScreenData(
    val balance: Int,
    val sellerName: String,
    val date: String
)