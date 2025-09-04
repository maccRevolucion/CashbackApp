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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import mx.diossa.cashbackapp.domain.usecases.ExchangeResult
import mx.diossa.cashbackapp.ui.components.ButtonBottomComponent
import mx.diossa.cashbackapp.ui.components.HeaderTitleComponent
import mx.diossa.cashbackapp.ui.components.PrinterInfoCardComponent
import mx.diossa.cashbackapp.ui.components.ticketResume
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen

@Composable
fun ConfirmScreen(
    navController: NavHostController,
    exchangeViewModel: ExchangeViewModel,
    viewModel: ConfirmViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val exchangeState by exchangeViewModel.exchangeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData(exchangeState.ticketDetails, exchangeState.selectedProducts)
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { result ->
            val state = viewModel.uiState.value
            exchangeViewModel.setStatusData(
                isAvailable = result is ExchangeResult.Success,
                isConnected = state.isPrinterConnected,
                ticket = state.confirmationData,
                products = state.selectedProducts
            )

            when (result) {
                is ExchangeResult.Success -> {
                    navController.navigate("${Screen.StatusExchange.route}/true/${state.isPrinterConnected}")
                }
                else -> {
                    navController.navigate("${Screen.StatusExchange.route}/false/${state.isPrinterConnected}")
                }
            }
        }
    }

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

                if (uiState.confirmationData != null) {
                    ticketResume(
                        data = uiState.confirmationData!!,
                        selectedProducts = uiState.selectedProducts,
                        total = uiState.total
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PrinterInfoCardComponent(isConnected = uiState.isPrinterConnected)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ButtonBottomComponent {
                    val ticket = exchangeState.ticketDetails
                    val products = exchangeState.selectedProducts
                    if (ticket != null && products.isNotEmpty()) {
                        viewModel.onConfirmAndProcess(ticket, products)
                    }
                }
            }
        }
    }
}
