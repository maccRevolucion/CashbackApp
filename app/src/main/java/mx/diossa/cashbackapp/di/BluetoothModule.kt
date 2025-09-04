package mx.diossa.cashbackapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun providePrinterConnection(@ApplicationContext context: Context): PrinterConnection {
        return PrinterConnection.getInstance(context)
    }
}