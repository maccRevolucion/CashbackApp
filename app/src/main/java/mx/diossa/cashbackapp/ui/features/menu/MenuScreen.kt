package mx.diossa.cashbackapp.ui.features.menu

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ItemButtons
import mx.diossa.cashbackapp.ui.components.ItemTicketsPerDay
import mx.diossa.cashbackapp.ui.components.TextUserHeader
import mx.diossa.cashbackapp.ui.components.recentActivity
import mx.diossa.cashbackapp.ui.features.navegation.Screen

@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: MenuViewModel = hiltViewModel(),
    onLogout: ()-> Unit
){
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
    val recentTickets by viewModel.recentTickets.collectAsState()
    val ticketsCount by viewModel.ticketsCount.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val employeeName by viewModel.employeeName.collectAsState()
    var showLogoutMenu by remember { mutableStateOf(false) }
    var showClosingDayDialog by remember { mutableStateOf(false) }
    var inputConfirm by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.refreshRecentTickets()
    }

    LaunchedEffect(navigationEvent){
        when(navigationEvent){
            MenuViewModel.NavigationEvent.NavigateToScanQR -> {
                navController.navigate("scan"){
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                viewModel.clearNavigationEvent()
            }
            MenuViewModel.NavigationEvent.NavigateToHistory ->{
                navController.navigate("history"){
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                viewModel.clearNavigationEvent()
            }

            MenuViewModel.NavigationEvent.NavigateToLogin -> {
                onLogout()
                viewModel.clearNavigationEvent()
            }
            null -> {/* Sin accion */}
        }
    }

    Column {
        TextUserHeader(
            employeeName = employeeName,
            onProfileClick = { showLogoutMenu = true },
            showLogoutMenu = showLogoutMenu,
            onDismissMenu = { showLogoutMenu = false },
            onLogoutClick = {
                showLogoutMenu = false
                viewModel.onLogoutClicked()
            },
            onClosingDay = { showClosingDayDialog = true }
        )
        ItemTicketsPerDay(tickets = ticketsCount.toString())
        Row {
            ItemButtons(
                action = "Escanear QR",
                subtitle = "Procesar un ticket",
                icon = Icons.Filled.QrCode2,
                onClick = {viewModel.onScanQRClicked()}
            )

            ItemButtons(
                action = "Historial",
                subtitle = "Ver tickets procesados",
                icon = Icons.Filled.History,
                onClick = {viewModel.onHistoryClicked()}
            )
        }
        if(isLoading){
            CircularProgressIndicator()
        } else {
            recentActivity(tickets = recentTickets, viewModel = viewModel)
        }
    }

    if (showClosingDayDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showClosingDayDialog = false },
            title = { Text("Confirmar Cierre de Día") },
            text = {
                Column {
                    Text("Esta acción generará el reporte y bloqueará el acceso hasta mañana.\n\nEscribe 'si' para confirmar:")
                    androidx.compose.material3.OutlinedTextField(
                        value = inputConfirm,
                        onValueChange = { inputConfirm = it },
                        placeholder = { Text("Escribe 'si'") }
                    )
                }
            },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        if (inputConfirm.equals("si", ignoreCase = true)) {
                            viewModel.onClosingDay()
                            showClosingDayDialog = false
                        } else {
                            Toast.makeText(navController.context, "Debes escribir 'si' para confirmar", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) { Text("Confirmar") }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showClosingDayDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
