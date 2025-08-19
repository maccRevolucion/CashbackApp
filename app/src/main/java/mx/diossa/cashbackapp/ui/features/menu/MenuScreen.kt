package mx.diossa.cashbackapp.ui.features.menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ItemButtons
import mx.diossa.cashbackapp.ui.components.ItemTicketsPerDay
import mx.diossa.cashbackapp.ui.components.TextUserHeader
import mx.diossa.cashbackapp.ui.components.recentActivity
import mx.diossa.cashbackapp.ui.features.navegation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuScreen(navController: NavHostController, viewModel: MenuViewModel = hiltViewModel()){
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
    val recentTickets by viewModel.recentTickets.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
            null -> {/* Sin accion */}
        }
    }

    Column {
        TextUserHeader(employeeName = "Salomon")
        ItemTicketsPerDay(tickets = "12")
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
}