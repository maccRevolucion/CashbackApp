package mx.diossa.cashbackapp.ui.features.printer

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import mx.diossa.cashbackapp.core.utils.PrinterReconnectWorker
import mx.diossa.cashbackapp.ui.components.HeaderTitle
import mx.diossa.cashbackapp.ui.components.PrinterConfig
import mx.diossa.cashbackapp.ui.components.printerStatus
import java.util.concurrent.TimeUnit

@Composable
fun PrinterScreen(navController: NavHostController, viewModel: PrinterViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            // No es necesario llamar a checkConnection, el ViewModel ya observa el estado.
        } else {
            // Puedes mostrar un mensaje más explícito al usuario aquí.
        }
    }

    LaunchedEffect(Unit) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        permissionLauncher.launch(permissions)
        val reconnectRequest = PeriodicWorkRequestBuilder<PrinterReconnectWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "printerReconnectWork",
            ExistingPeriodicWorkPolicy.KEEP,
            reconnectRequest
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderTitle()
            Spacer(modifier = Modifier.height(16.dp))
            printerStatus(
                printerName = uiState.printerName,
                isConnected = uiState.isConnected,
                onRefresh = {},
                onPrinttest = {viewModel},
                message = "Impresión de Prueba Exitosa"
            )

            Spacer(modifier = Modifier.height(24.dp))
            PrinterConfig(
                printerName = uiState.printerName,
                macAddress = uiState.macAddress,
                onNameChanged = { viewModel.onNameChanged(it) },
                onMacChanged = { viewModel.onMacChanged(it) },
                onSave = { mac -> viewModel.saveConfig(mac) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            if (uiState.error != null) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}