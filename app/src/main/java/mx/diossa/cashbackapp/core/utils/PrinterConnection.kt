package mx.diossa.cashbackapp.core.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.diossa.cashbackapp.core.utils.BluetoothService.Companion.STATE_CONNECTED
import java.lang.ref.WeakReference

data class PrinterStatus(
    val state: Int = BluetoothService.STATE_NONE,
    val deviceName: String = "---"
)

@SuppressLint("MissingPermission")
class PrinterConnection private constructor(private val context: WeakReference<Context>) {
    private val _printerStatus = MutableStateFlow(PrinterStatus())
    val printerStatus = _printerStatus.asStateFlow()

    var bluetoothService: BluetoothService?
    private var bluetoothDevice: BluetoothDevice? = null
    private var macPrinter: String
    private val preferences: SharedPreferences? = context.get()?.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                BluetoothService.MESSAGE_STATE_CHANGE -> {
                    _printerStatus.value = _printerStatus.value.copy(state = msg.arg1)
                    if (msg.arg1 != BluetoothService.STATE_CONNECTED) {
                        _printerStatus.value = _printerStatus.value.copy(deviceName = "---")
                    }
                }
                BluetoothService.MESSAGE_DEVICE_NAME -> {
                    val deviceName = msg.obj as String
                    _printerStatus.value = _printerStatus.value.copy(deviceName = deviceName)
                    Log.d("PrinterConnection", "Connected to: $deviceName")
                }
                BluetoothService.MESSAGE_WRITE -> {
                    // Se escribió algo
                }
                BluetoothService.MESSAGE_READ -> {
                    // Se leyó algo.
                }
            }
        }
    }

    init {
        macPrinter = preferences?.getString("macPrinter", "00:00:00:00:00:00") ?: "00:00:00:00:00:00"
        bluetoothService = context.get()?.let { BluetoothService(it, mHandler) }
        if (macPrinter != "00:00:00:00:00:00") {
            connectPrinter()
        }
    }

    fun updateMacAndConnect(mac: String) {
        Log.d("PrinterConnection", "Updating MAC to $mac")
        bluetoothService?.stop()
        macPrinter = mac
        preferences?.edit()?.putString("macPrinter", mac)?.apply()
        connectPrinter()
    }

    private fun connectPrinter() {
        if (!isBluetoothOn() || macPrinter == "00:00:00:00:00:00") return

        try {
            if (bluetoothService?.getState() == BluetoothService.STATE_NONE) {
                bluetoothService?.start()
            }
            bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macPrinter)
            bluetoothDevice?.let {
                bluetoothService?.connect(it)
                Log.d("PrinterConnection", "Initiating connect to MAC: $macPrinter")
            }
        } catch (e: IllegalArgumentException) {
            Log.e("PrinterConnection", "Invalid MAC Address: $macPrinter", e)
            _printerStatus.value = _printerStatus.value.copy(state = BluetoothService.STATE_NONE)
        }
    }

    fun reconnectPrinter() {
        if (isBluetoothOn()) {
            connectPrinter()
        }
    }

    fun stopBluetoothService() {
        bluetoothService?.stop()
    }

    fun isConnected(): Boolean {
        return _printerStatus.value.state == BluetoothService.STATE_CONNECTED
    }

    fun sendMessage(message: String): Boolean {
        if (!isConnected()) return false
        bluetoothService?.sendMessage(message, "GBK")
        return true
    }

    fun sendImage(image: ByteArray): Boolean {
        if (!isConnected()) return false
        bluetoothService?.sendImage(image)
        return true
    }

    companion object {
        @Volatile
        private var instance: PrinterConnection? = null

        fun getInstance(context: Context): PrinterConnection {
            return instance ?: synchronized(this) {
                instance ?: PrinterConnection(WeakReference(context.applicationContext)).also { instance = it }
            }
        }

        fun isBluetoothOn(): Boolean {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            return bluetoothAdapter != null && bluetoothAdapter.isEnabled
        }
    }
}