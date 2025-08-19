package mx.diossa.cashbackapp.core.utils

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.IBinder

class BluetoothBackgroundService : Service() {
    private var workerThread: Thread? = null
    private val WAIT_TIME = 5000L

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (workerThread == null || !workerThread!!.isAlive) {
            workerThread = Thread {
                try {
                    while (true) {
                        Thread.sleep(WAIT_TIME)

                        if (!PrinterConnection.isBluetoothOn()) {
                            BluetoothAdapter.getDefaultAdapter().enable()
                            Thread.sleep(WAIT_TIME)
                        } else {
                            if (PrinterConnection.isInstanceCreate() && PrinterConnection.getInstance(applicationContext)!!.isConnectionLost()) {
                                PrinterConnection.getInstance(applicationContext)!!.reconnectPrinter()
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            workerThread?.start()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
}