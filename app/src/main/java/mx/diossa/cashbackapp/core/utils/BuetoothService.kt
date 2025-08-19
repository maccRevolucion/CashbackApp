package mx.diossa.cashbackapp.core.utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.util.UUID

class BluetoothService(private val context: Context, private val handler: Handler) {
    companion object {
        private const val TAG = "BluetoothService"
        const val STATE_NONE = 0
        const val STATE_LISTEN = 1
        const val STATE_CONNECTING = 2
        const val STATE_CONNECTED = 3

        const val MESSAGE_STATE_CHANGE = 1
        const val MESSAGE_READ = 2
        const val MESSAGE_WRITE = 3
        const val MESSAGE_DEVICE_NAME = 4

        private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        private const val NAME = "BTPrinter"
    }

    private val mAdapter = BluetoothAdapter.getDefaultAdapter()
    private var mAcceptThread: AcceptThread? = null
    private var mConnectThread: ConnectThread? = null
    private var mConnectedThread: ConnectedThread? = null
    private var mState = STATE_NONE

    @Synchronized
    fun getState(): Int = mState

    @Synchronized
    fun start() {
        mConnectThread?.cancel()
        mConnectThread = null
        mConnectedThread?.cancel()
        mConnectedThread = null
        if (mAcceptThread == null) {
            mAcceptThread = AcceptThread()
            mAcceptThread?.start()
        }
    }

    @Synchronized
    fun connect(device: BluetoothDevice) {
        if (mState == STATE_CONNECTING) {
            mConnectThread?.cancel()
            mConnectThread = null
        }
        mConnectedThread?.cancel()
        mConnectedThread = null
        mConnectThread = ConnectThread(device)
        mConnectThread?.start()
    }

    @Synchronized
    fun connected(socket: BluetoothSocket, device: BluetoothDevice) {
        mConnectThread?.cancel()
        mConnectThread = null
        mConnectedThread?.cancel()
        mConnectedThread = null
        mAcceptThread?.cancel()
        mAcceptThread = null
        mConnectedThread = ConnectedThread(socket)
        mConnectedThread?.start()
        val msg = handler.obtainMessage(MESSAGE_DEVICE_NAME)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        msg.obj = device.name
        handler.sendMessage(msg)
    }

    @Synchronized
    fun stop() {
        mConnectThread?.cancel()
        mConnectThread = null
        mConnectedThread?.cancel()
        mConnectedThread = null
        mAcceptThread?.cancel()
        mAcceptThread = null
        setState(STATE_NONE)
    }

    fun sendMessage(message: String, charset: String) {
        if (mState != STATE_CONNECTED) return
        try {
            val bytes = message.toByteArray(charset(charset))
            mConnectedThread?.write(bytes)
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Unsupported charset", e)
        }
    }

    fun sendImage(image: ByteArray) {
        if (mState != STATE_CONNECTED) return
        mConnectedThread?.write(image)
    }

    private fun setState(state: Int) {
        mState = state
        handler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget()
    }

    private inner class AcceptThread : Thread() {
        private val mmServerSocket: BluetoothServerSocket? by lazy {
            try {
                mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID)
            } catch (e: IOException) {
                Log.e(TAG, "listen() failed", e)
                null
            }
        }

        override fun run() {
            var socket: BluetoothSocket?
            while (mState != STATE_CONNECTED) {
                socket = try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
                    Log.e(TAG, "accept() failed", e)
                    null
                }
                socket?.also {
                    synchronized(this@BluetoothService) {
                        when (mState) {
                            STATE_LISTEN, STATE_CONNECTING -> connected(it, it.remoteDevice)
                            STATE_NONE, STATE_CONNECTED -> {
                                try {
                                    it.close()
                                } catch (e: IOException) {
                                    Log.e(TAG, "Could not close unwanted socket", e)
                                }
                            }
                        }
                    }
                }
            }
        }

        fun cancel() {
            try {
                mmServerSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "close() of server failed", e)
            }
        }
    }

    private inner class ConnectThread(private val mmDevice: BluetoothDevice) : Thread() {
        private val mmSocket: BluetoothSocket? by lazy {
            try {
                mmDevice.createRfcommSocketToServiceRecord(MY_UUID)
            } catch (e: IOException) {
                Log.e(TAG, "create() failed", e)
                null
            }
        }

        override fun run() {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            mAdapter.cancelDiscovery()
            mmSocket?.let { socket ->
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                    socket.connect()
                } catch (e: IOException) {
                    try {
                        socket.close()
                    } catch (e2: IOException) {
                        Log.e(TAG, "unable to close() socket during connection failure", e2)
                    }
                    connectionFailed()
                    return
                }
                synchronized(this@BluetoothService) {
                    mConnectThread = null
                }
                connected(socket, mmDevice)
            }
        }

        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "close() of connect socket failed", e)
            }
        }
    }

    private inner class ConnectedThread(private val mmSocket: BluetoothSocket) : Thread() {
        private val mmInStream: InputStream? = try { mmSocket.inputStream } catch (e: IOException) { null }
        private val mmOutStream: OutputStream? = try { mmSocket.outputStream } catch (e: IOException) { null }

        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true) {
                try {
                    bytes = mmInStream?.read(buffer) ?: break
                    handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget()
                } catch (e: IOException) {
                    connectionLost()
                    break
                }
            }
        }

        fun write(buffer: ByteArray) {
            try {
                mmOutStream?.write(buffer)
                handler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer).sendToTarget()
            } catch (e: IOException) {
                Log.e(TAG, "Exception during write", e)
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(TAG, "close() of connect socket failed", e)
            }
        }
    }

    fun connectionFailed() {
        setState(STATE_LISTEN)
    }

    fun connectionLost() {
        setState(STATE_LISTEN)
    }
}