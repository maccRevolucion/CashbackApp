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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import mx.diossa.cashbackapp.domain.model.Ticket
import mx.diossa.cashbackapp.ui.components.HeaderTicketComponent
import mx.diossa.cashbackapp.ui.components.ImportantInfoCard
import mx.diossa.cashbackapp.ui.components.ticketDetails
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TicketCheckScreen(
    navController: NavHostController,
    exchangeViewModel: ExchangeViewModel,
    viewModel: TicketCheckViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val details = uiState.details

    LaunchedEffect(Unit) {
        viewModel.init(exchangeViewModel)
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
        ) {
            HeaderTicketComponent(onBack = { navController.popBackStack() } )
            Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))


            if (details != null) {
                val formattedDate = details.cashbackDate?.let { dateString ->
                    try {
                        LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } catch (e: Exception) {
                        dateString
                    }
                } ?: "Fecha no disponible"
                 ticketDetails(
                     ticketNumber = details.idCashback,
                     date = formattedDate,
                     sellerName = details.nameEmployee,
                     availableAmount = details.cashbackValue,
                     onSelect = {navController.navigate(Screen.Products.route) },
                     isValid = uiState.isValid
                 )
            }


            Spacer(modifier= Modifier
                .fillMaxWidth()
                .height(10.dp))
            ImportantInfoCard()
        }
    }
}
