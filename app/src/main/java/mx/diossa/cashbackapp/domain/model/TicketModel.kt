package mx.diossa.cashbackapp.domain.model

import mx.diossa.cashbackapp.data.entity.TicketEntity
import java.time.LocalDateTime

data class Ticket(
    val idFolio: String,
    val idTicket: String,
    val sellerName: String,
    val date: LocalDateTime,
    val cash: Int
)

data class UiStateTicket(
    val query: String = "",
    val isAscending: Boolean = false,
    val filteredTickets: List<TicketEntity> = emptyList()
)