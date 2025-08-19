package mx.diossa.cashbackapp.data.datasource.local

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import mx.diossa.cashbackapp.data.dao.TicketDao
import mx.diossa.cashbackapp.data.entity.TicketEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TicketLocalDataSource @Inject constructor(
    private val ticketDao: TicketDao
){
    suspend fun getRecentCompleted(): List<TicketEntity> = ticketDao.getRecentCompleted()

    suspend fun getAllCompleted(): List<TicketEntity> = ticketDao.getAllCompleted()

    suspend fun initializeTicketsIfNeeded() {
        Log.d("TicketInit", "Count: ${ticketDao.getCount()}")
        if (ticketDao.getCount() == 0) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val initialTickets = listOf(
                TicketEntity("1", "T-1001", "Juan Perez", LocalDateTime.parse("2025-08-18 10:00", formatter), 250, "completed"),
                TicketEntity("2", "T-1002", "Maria Lopez", LocalDateTime.parse("2025-08-18 14:30", formatter), 150, "completed"),
                TicketEntity("3", "T-1003", "Carlos Garcia", LocalDateTime.parse("2025-08-18 09:45", formatter), 300, "completed"),
                TicketEntity("4", "T-1004", "Ana Martinez", LocalDateTime.parse("2025-08-18 16:20", formatter), 200, "completed"),
                TicketEntity("5", "T-1005", "Luis Rodriguez", LocalDateTime.parse("2025-08-18 11:10", formatter), 100, "completed"),
                TicketEntity("6", "T-1006", "Sofia Hernandez", LocalDateTime.parse("2025-08-18 13:00", formatter), 400, "completed"),
                TicketEntity("7", "T-1007", "Diego Torres", LocalDateTime.parse("2025-08-18 15:30", formatter), 350, "completed"),
                TicketEntity("8", "T-1008", "Elena Ramirez", LocalDateTime.parse("2025-08-18 17:45", formatter), 50, "failed")
            )
            Log.d("TicketInit", "Inserted ${initialTickets.size} tickets")
            ticketDao.insertAll(initialTickets)
        }
        Log.d("TicketInit", "DB not empty, skipping insert")
    }
}