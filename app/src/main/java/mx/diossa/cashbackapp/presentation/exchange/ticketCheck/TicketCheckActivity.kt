package mx.diossa.cashbackapp.presentation.exchange.ticketCheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun TicketCheckScreen(navController: NavHostController, viewModel: TicketCheckViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
        ) {
            HeaderComponent(onBack = { navController.popBackStack() } )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            ticketDetails(onSelect = {navController.navigate("products")})
            Spacer(modifier= Modifier
                .fillMaxWidth()
                .height(10.dp))
            ImportantInfoCard()
        }
    }
}
