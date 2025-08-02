package mx.diossa.cashbackapp.presentation.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MenuScreen(navController: NavHostController){
    Column {
        TextUserHeader()
        ItemTicketsPerDay(tickets = "12")
        Row {
            ItemButtons(action = "Escanear QR", subtitle = "Procesar un ticket", icon = Icons.Filled.QrCode2, onClick = {})
            ItemButtons(action = "Historial", subtitle = "Ver tickets procesados", icon = Icons.Filled.History, onClick = {})
        }
        recentActivity()
    }
}