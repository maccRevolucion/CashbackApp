package mx.diossa.cashbackapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.diossa.cashbackapp.data.entity.TicketEntity

@Dao
interface TicketDao{
    @Query("SELECT * FROM tickets WHERE status = 'complete' ORDER BY date DESC LIMIT 3")
    suspend fun getRecentCompleted(): List<TicketEntity>

    @Query("SELECT * FROM tickets WHERE status = 'completed' ORDER BY date DESC")
    suspend fun getAllCompleted(): List<TicketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tickets: List<TicketEntity>)

    @Query("SELECT COUNT(*) FROM tickets")
    suspend fun getCount(): Int
}