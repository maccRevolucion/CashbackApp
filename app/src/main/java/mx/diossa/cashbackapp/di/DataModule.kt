package mx.diossa.cashbackapp.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.diossa.cashbackapp.data.AppDatabase
import mx.diossa.cashbackapp.data.dao.TicketDao
import mx.diossa.cashbackapp.data.dao.UserDao
import mx.diossa.cashbackapp.data.datasource.local.TicketLocalDataSource
import mx.diossa.cashbackapp.data.datasource.local.UserLocalDataSource
import mx.diossa.cashbackapp.data.repository.TicketRepository
import mx.diossa.cashbackapp.data.repository.UserRepository
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,"app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()


    @Provides
    @Singleton
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource{
        return UserLocalDataSource(userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(localDataSource: UserLocalDataSource): UserRepository{
        return UserRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideTicketDao(database: AppDatabase): TicketDao = database.ticketDao()

    @Provides
    @Singleton
    fun provideTicketLocalDataSource(ticketDao: TicketDao): TicketLocalDataSource = TicketLocalDataSource(ticketDao)

    @Provides
    @Singleton
    fun provideTicketRepository(localDataSource: TicketLocalDataSource): TicketRepository = TicketRepository(localDataSource)
}