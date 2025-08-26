package mx.diossa.cashbackapp.data.repository

import mx.diossa.cashbackapp.data.local.datasource.TicketLocalDataSource
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val localDataSource: TicketLocalDataSource
) {

    suspend fun getRecentComplete(): List<TicketEntity>{
        localDataSource.initializeTicketsIfNeeded()
        return localDataSource.getRecentCompleted()
    }

    suspend fun getAllCompleted(): List<TicketEntity>{
        localDataSource.initializeTicketsIfNeeded()
        return localDataSource.getAllCompleted()
    }
}