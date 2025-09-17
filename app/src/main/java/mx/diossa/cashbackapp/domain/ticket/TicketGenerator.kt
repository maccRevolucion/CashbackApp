package mx.diossa.cashbackapp.domain.ticket

import kotlinx.coroutines.flow.StateFlow
import mx.diossa.cashbackapp.data.local.entity.TicketWithProducts
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
            val maxNameLength = 12
            val nameChunks = item.name.chunked(maxNameLength)
            val quantity = item.quantity.toString().padStart(3)
            val price = df.format(item.price).padStart(6)
            val sub = df.format(itemSubtotal).padStart(7)

            appendLine("${nameChunks.first().padEnd(maxNameLength)} $quantity $price $sub")
            nameChunks.drop(1).forEach { chunk ->
                appendLine(chunk.padEnd(maxNameLength))
            }
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


    fun buildDailySummaryTicket(tickets: List<TicketWithProducts>, employeeName: String): String = buildString {
        val df = DecimalFormat("#0.00")
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))

        appendLine("                             ")
        appendLine("             DSD             ")
        appendLine("******* CORTE DE DIA *******")
        appendLine("Fecha: $date")
        appendLine("Almacenista: $employeeName")
        appendLine("-----------------------------")

        var totalCashback = 0.0
        val productSummary = mutableMapOf<String, Pair<Int, Double>>()

        tickets.forEach { ticket ->
            totalCashback += ticket.ticketEntity.amount
            ticket.products.forEach { product ->
                val current = productSummary[product.productName] ?: (0 to 0.0)
                productSummary[product.productName] = (
                        current.first + product.quantity
                        ) to (
                        current.second + (product.quantity * product.price)
                        )
            }
        }

        appendLine("PRODUCTOS ENTREGADOS:")

        val maxWidth = 20

        productSummary.forEach { (name, pair) ->
            val (quantity, total) = pair
            val priceString = "x$quantity  $${df.format(total)}"
            val wrappedLines = name.chunked(maxWidth)

            wrappedLines.forEachIndexed { index, line ->
                if (index == 0) {
                    appendLine(line.padEnd(maxWidth) + " " + priceString)
                } else {
                    appendLine(line)
                }
            }
        }

        appendLine("-----------------------------")
        appendLine("Total Tickets: ${tickets.size}")
        appendLine("Total Cashback: $${df.format(totalCashback)}")
        appendLine("-----------------------------")
        appendLine("Fin del reporte del dia")
        appendLine("                             ")
        appendLine("                             ")
    }

}