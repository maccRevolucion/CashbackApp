package mx.diossa.cashbackapp.domain.ticket

import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class RedemptionItem(val name: String, val quantity: Int, val price: Double)

class TicketGenerator @Inject constructor() {
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm a", Locale.getDefault())

    fun buildTestTicket(): String = buildString {
        val date = LocalDateTime.now().format(formatter)
        appendLine("                             ")
        appendLine("                             ")
        appendLine("***** TEST DE IMPRESORA *****")
        appendLine("Fecha: $date")
        appendLine("-----------------------------")
        appendLine("Conexion exitosa!")
        appendLine("Impresora lista.")
        appendLine("-----------------------------")
        appendLine("Fin de prueba.")
        appendLine("                             ")
        appendLine("                             ")
    }

    fun buildRedemptionTicket(
        items: List<RedemptionItem>,
        cashback: Double,
        subtotal: Double,
        vendor: String,
        total: Double
    ): String = buildString {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val date = LocalDateTime.now().format(formatter)

        val df = DecimalFormat("#0.00")
        val excessCashback = cashback - subtotal

        appendLine("                             ")
        appendLine("             DSD             ")
        appendLine("******* CANJE CASHBACK *******")
        appendLine("Fecha: $date")
        appendLine("Vendedor: $vendor")
        appendLine("CEDIS: MAZATLAN")
        appendLine("-----------------------------")
        appendLine("Producto    Cant. Precio  Sub.")

        items.forEach { item ->
            val itemSubtotal = item.quantity * item.price
            val name = if (item.name.length > 12) {
                item.name.take(12)
            } else {
                item.name.padEnd(12)
            }

            val quantity = item.quantity.toString().padStart(3)
            val price = df.format(item.price).padStart(6)
            val sub = df.format(itemSubtotal).padStart(7)

            appendLine("$name $quantity $price $sub")
        }

        appendLine("                             ")
        appendLine("-----------DESGLOSE----------")
        appendLine("Subtotal: $${df.format(subtotal)}")
        appendLine("Cashback restante: $${df.format(excessCashback)}")
        appendLine("Total cashback usado: $${df.format(subtotal)}")
        appendLine("-----------------------------")
        appendLine("Gracias por tu canje!")
        appendLine("Vuelve pronto :)")
        appendLine("                             ")
        appendLine("                             ")
    }
}