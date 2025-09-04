package mx.diossa.cashbackapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import java.time.LocalDateTime

@Dao
interface TicketDao{
    @Query("SELECT * FROM tickets WHERE status = 'completed' AND date >= :startOfDay ORDER BY date DESC LIMIT 4")
    suspend fun getRecentCompleted(startOfDay: LocalDateTime): List<TicketEntity>


    @Query("SELECT * FROM tickets WHERE status = 'completed' ORDER BY date DESC")
    suspend fun getAllCompleted(): List<TicketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tickets: List<TicketEntity>)

    @Query("SELECT COUNT(*) FROM tickets")
    suspend fun getCount(): Int
}