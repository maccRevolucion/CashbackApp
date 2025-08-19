package mx.diossa.cashbackapp.ui.features.printer

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.update
import mx.diossa.cashbackapp.core.utils.BluetoothBackgroundService
import mx.diossa.cashbackapp.ui.components.HeaderTitle
import mx.diossa.cashbackapp.ui.components.PrinterConfig
import mx.diossa.cashbackapp.ui.components.printerStatus

@Composable
fun PrinterScreen(navController: NavHostController, viewModel: PrinterViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            viewModel.checkConnection()
        } else {
            viewModel._uiState.update { it.copy(error = "Permisos denegados") }
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
        context.startService(Intent(context, BluetoothBackgroundService::class.java))
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
        ) {
            HeaderTitle()
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            printerStatus(printerName = uiState.printerName, isConnected = uiState.isConnected)
            Spacer(modifier= Modifier
                .fillMaxWidth()
                .height(18.dp))
            PrinterConfig(
                printerName = uiState.printerName,
                macAddress = uiState.macAddress,
                onNameChanged = { viewModel.onNameChanged(it) },
                onMacChanged = { viewModel.onMacChanged(it) },
                onSave = { name, mac -> viewModel.saveConfig(name, mac) }
            )
        }
    }
}