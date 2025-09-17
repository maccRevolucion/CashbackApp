package mx.diossa.cashbackapp.data.repository

import kotlinx.coroutines.flow.Flow
import mx.diossa.cashbackapp.data.local.datasource.TicketLocalDataSource
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.local.entity.TicketProductEntity
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val localDataSource: TicketLocalDataSource
) {
    fun getRecentCompleteForToday(startOfDay: LocalDateTime): Flow<List<TicketEntity>> =
        localDataSource.getRecentCompletedForToday(startOfDay)

    suspend fun getAllCompleted(startOfDay: LocalDateTime): Flow<List<TicketEntity>>{
        localDataSource.initializeTicketsIfNeeded()
        return localDataSource.getAllCompleted(startOfDay)
    }

    suspend fun insertTicket(ticket: TicketEntity) {
        localDataSource.insert(ticket)
    }

    suspend fun clearAll(){
        localDataSource.clearAll()
    }

    suspend fun insertTicketWithProducts(ticket: TicketEntity, products: List<TicketProductEntity>) {
        localDataSource.insertTicketWithProducts(ticket, products)
    }

    fun getTicketsWithProductsForDate(date: LocalDate) =
        localDataSource.getTicketsWithProductsForDate(date)

}