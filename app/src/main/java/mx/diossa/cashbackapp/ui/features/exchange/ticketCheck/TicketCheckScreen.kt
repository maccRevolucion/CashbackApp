package mx.diossa.cashbackapp.ui.features.exchange.ticketCheck

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
import mx.diossa.cashbackapp.ui.components.HeaderTicketComponent
import mx.diossa.cashbackapp.ui.components.ImportantInfoCard
import mx.diossa.cashbackapp.ui.components.ticketDetails

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
            HeaderTicketComponent(onBack = { navController.popBackStack() } )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            ticketDetails(
                onSelect = {navController.navigate("products")},
                isValid = true
            )
            Spacer(modifier= Modifier
                .fillMaxWidth()
                .height(10.dp))
            ImportantInfoCard()
        }
    }
}
