package mx.diossa.cashbackapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey val id: String,
    val ticketNumber: String,
    val sellerName: String,
    val date: LocalDateTime,
    val amount: Int,
    val status: String
)