package mx.diossa.cashbackapp.core.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PrinterReconnectWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("PrinterReconnectWorker", "Worker running...")

        val printerConnection = PrinterConnection.getInstance(applicationContext)

        if (!PrinterConnection.isBluetoothOn()) {
            Log.d("PrinterReconnectWorker", "Bluetooth is off. Worker stopping.")
            return Result.success()
        }

        if (!printerConnection.isConnected()) {
            Log.d("PrinterReconnectWorker", "Printer is disconnected. Attempting to reconnect.")
            printerConnection.reconnectPrinter()
        } else {
            Log.d("PrinterReconnectWorker", "Printer is already connected.")
        }

        return Result.success()
    }
}