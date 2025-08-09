package mx.diossa.cashbackapp.ui.features.exchange.confirm

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ButtonBottomComponent
import mx.diossa.cashbackapp.ui.components.HeaderTitleComponent
import mx.diossa.cashbackapp.ui.components.PrinterInfoCardComponent
import mx.diossa.cashbackapp.ui.components.ticketResume

@Composable
fun confirmScreen(navController: NavHostController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderTitleComponent(onBack = { navController.popBackStack() })
                Spacer(modifier = Modifier.height(18.dp))
                ticketResume()
                Spacer(modifier = Modifier.height(10.dp))
                PrinterInfoCardComponent()
            }
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)){
                ButtonBottomComponent {navController.navigate("exchange") }
            }
        }
    }
}