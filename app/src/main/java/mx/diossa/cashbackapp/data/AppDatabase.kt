package mx.diossa.cashbackapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.diossa.cashbackapp.core.utils.Converters
import mx.diossa.cashbackapp.data.local.dao.TicketDao
import mx.diossa.cashbackapp.data.local.dao.UserDao
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.local.entity.TicketProductEntity
import mx.diossa.cashbackapp.data.local.entity.TicketWithProducts
import mx.diossa.cashbackapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, TicketEntity::class, TicketProductEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao
}