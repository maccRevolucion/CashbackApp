package mx.diossa.cashbackapp.core.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

class PrinterConnection private constructor(private val context: WeakReference<Context>) {
    companion object {
        private var instance: PrinterConnection? = null

        fun getInstance(context: Context): PrinterConnection? {
            if (isBluetoothOn()) {
                if (instance == null) {
                    instance = PrinterConnection(WeakReference(context))
                    instance?.connectPrinter()
                }
            }
            return instance
        }

        fun isBluetoothOn(): Boolean {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            return bluetoothAdapter != null && bluetoothAdapter.isEnabled
        }

        fun isInstanceCreate(): Boolean = instance != null
    }

    private var bluetoothService: BluetoothService? = null
    private var bluetoothDevice: BluetoothDevice? = null
    private var macPrinter: String
    var state = BluetoothService.STATE_NONE
    var what = -1

    init {
        val preferences = context.get()?.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        macPrinter = preferences?.getString("macPrinter", "00:00:00:00:00:00") ?: "00:00:00:00:00:00"
    }

    fun connectPrinter() {
        connectBluetoothService()
    }

    fun reconnectPrinter() {
        if (isBluetoothOn()) reconnectBluetoothService()
    }

    private fun connectBluetoothService() {
        bluetoothService = BluetoothService(context.get()!!, Handler(Looper.getMainLooper()) { msg ->
            state = if (msg.what != BluetoothService.MESSAGE_STATE_CHANGE) msg.arg1 else state
            what = if (msg.what != BluetoothService.MESSAGE_STATE_CHANGE) msg.what else what
            false
        })
        bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macPrinter)
        bluetoothService?.connect(bluetoothDevice!!)
    }

    private fun reconnectBluetoothService() {
        bluetoothService?.stop()
        bluetoothService?.connect(bluetoothDevice!!)
    }

    fun stopBluetoothService() {
        bluetoothService?.stop()
    }

    fun isConnectionLost(): Boolean {
        return state != BluetoothService.STATE_CONNECTED &&
                state != BluetoothService.STATE_LISTEN &&
                what != BluetoothService.MESSAGE_WRITE &&
                what != BluetoothService.MESSAGE_DEVICE_NAME
    }

    fun sendMessage(message: String): Boolean {
        bluetoothService?.sendMessage(message, "GBK")
        return true
    }

    fun sendImage(image: ByteArray): Boolean {
        bluetoothService?.sendImage(image)
        return true
    }
}