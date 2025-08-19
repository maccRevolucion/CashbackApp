package mx.diossa.cashbackapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import mx.diossa.cashbackapp.data.datasource.local.TicketLocalDataSource
import mx.diossa.cashbackapp.data.entity.TicketEntity
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
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