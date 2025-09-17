package mx.diossa.cashbackapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TicketWithProducts(
    @Embedded val ticketEntity: TicketEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ticketId"
    )
    val products: List<TicketProductEntity>
)