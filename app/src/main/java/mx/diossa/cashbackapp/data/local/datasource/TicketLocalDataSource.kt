package mx.diossa.cashbackapp.data.local.datasource

import android.util.Log
import kotlinx.coroutines.flow.Flow
import mx.diossa.cashbackapp.data.local.dao.TicketDao
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.local.entity.TicketProductEntity
import mx.diossa.cashbackapp.data.local.entity.TicketWithProducts
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TicketLocalDataSource @Inject constructor(
    private val ticketDao: TicketDao
){
    private val startOfToday: LocalDateTime = LocalDateTime.now().toLocalDate().atStartOfDay()
    fun getRecentCompletedForToday(startOfDay: LocalDateTime): Flow<List<TicketEntity>> = ticketDao.getRecentCompleted(startOfToday)
    fun getAllCompleted(startOfDay: LocalDateTime): Flow<List<TicketEntity>> = ticketDao.getAllCompleted(startOfToday)
    suspend fun insert(ticket: TicketEntity) = ticketDao.insert(ticket)

    suspend fun insertProducts(products: List<TicketProductEntity>) =
        ticketDao.insertProducts(products)

    suspend fun insertTicketWithProducts(ticket: TicketEntity, products: List<TicketProductEntity>) {
        ticketDao.insert(ticket)
        ticketDao.insertProducts(products)
    }

    suspend fun clearAll(){
        ticketDao.clearAll()
    }

    fun getTicketsWithProductsForDate(date: LocalDate): Flow<List<TicketWithProducts>> {
        val start = date.atStartOfDay()
        val end = date.plusDays(1).atStartOfDay()
        return ticketDao.getTicketsForDate(start, end)
    }

    suspend fun initializeTicketsIfNeeded() {
        Log.d("TICKET_LOCAL_DATASOURCE", "Contador: ${ticketDao.getCount()}")
        if (ticketDao.getCount() == 0) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val initialTickets = listOf(
                TicketEntity("1", "T-1001", "Juan Perez", LocalDateTime.parse("2025-08-27 10:00", formatter), 250, "completed"),
                TicketEntity("2", "T-1002", "Maria Lopez", LocalDateTime.parse("2025-08-27 14:30", formatter), 150, "completed"),
                TicketEntity("3", "T-1003", "Carlos Garcia", LocalDateTime.parse("2025-08-27 09:45", formatter), 300, "completed"),
                TicketEntity("4", "T-1004", "Ana Martinez", LocalDateTime.parse("2025-08-27 16:20", formatter), 200, "completed"),
                TicketEntity("5", "T-1005", "Luis Rodriguez", LocalDateTime.parse("2025-08-27 11:10", formatter), 100, "completed"),
                TicketEntity("6", "T-1006", "Sofia Hernandez", LocalDateTime.parse("2025-08-27 13:00", formatter), 400, "completed"),
                TicketEntity("7", "T-1007", "Diego Torres", LocalDateTime.parse("2025-08-27 15:30", formatter), 350, "completed"),
                TicketEntity("8", "T-1008", "Elena Ramirez", LocalDateTime.parse("2025-08-27 17:45", formatter), 50, "failed")
            )
            Log.d("TICKET_LOCAL_DATASOURCE", "Insertado: ${initialTickets.size} tickets")
            ticketDao.insertAll(initialTickets)
        }
        Log.d("TICKET_LOCAL_DATASOURCE", "BD no vacia, ignoramos insertar")
    }
}