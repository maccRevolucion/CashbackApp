package mx.diossa.cashbackapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.local.entity.TicketProductEntity
import mx.diossa.cashbackapp.data.local.entity.TicketWithProducts
import java.time.LocalDateTime

@Dao
interface TicketDao{
    @Query("SELECT * FROM tickets WHERE status = 'completed' AND date >= :startOfDay ORDER BY date DESC LIMIT 4")
    fun getRecentCompleted(startOfDay: LocalDateTime): Flow<List<TicketEntity>>

    @Query("SELECT * FROM tickets WHERE status = 'completed' AND date >= :startOfDay ORDER BY date DESC")
    fun getAllCompleted(startOfDay: LocalDateTime): Flow<List<TicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tickets: List<TicketEntity>)

    @Query("SELECT COUNT(*) FROM tickets")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tickets: TicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<TicketProductEntity>)

    @Query("DELETE FROM tickets")
    suspend fun clearAll()

    @Transaction
    @Query("SELECT * FROM tickets WHERE date BETWEEN :start AND :end")
    fun getTicketsForDate(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<TicketWithProducts>>
}