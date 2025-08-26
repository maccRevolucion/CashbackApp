package mx.diossa.cashbackapp.domain.ticket

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
        storeName: String,
        items: List<RedemptionItem>,
        cashback: Double,
        subtotal: Double,
        total: Double
    ): String = buildString {
        val date = LocalDateTime.now().format(formatter)
        appendLine("                             ")
        appendLine("                             ")
        appendLine("*** $storeName - CANJE CASHBACK ***")
        appendLine("Fecha: $date")
        appendLine("-----------------------------")
        appendLine("Item          Cant.  Precio  Subtotal")
        items.forEach { item ->
            val itemSubtotal = item.quantity * item.price
            appendLine("${item.name.padEnd(12)} ${item.quantity.toString().padEnd(5)} $${item.price.toString().padEnd(6)} $$itemSubtotal")
        }
        appendLine("-----------------------------")
        appendLine("Subtotal: $$subtotal")
        appendLine("Cashback aplicado: -$$cashback")
        appendLine("Total: $$total")
        appendLine("-----------------------------")
        appendLine("Gracias por tu canje!")
        appendLine("Vuelve pronto :)")
        appendLine("                             ")
        appendLine("                             ")
    }
}