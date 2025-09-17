package mx.diossa.cashbackapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_products")
data class TicketProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ticketId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)
