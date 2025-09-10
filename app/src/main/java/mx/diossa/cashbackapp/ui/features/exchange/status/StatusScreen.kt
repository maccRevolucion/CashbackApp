package mx.diossa.cashbackapp.ui.features.exchange.status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Print
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
import mx.diossa.cashbackapp.ui.components.ButtonsBottomComponent
import mx.diossa.cashbackapp.ui.components.HeaderCenterComponent
import mx.diossa.cashbackapp.ui.components.HeaderTextComponent
import mx.diossa.cashbackapp.ui.components.InfoCardNotInventoryComponent
import mx.diossa.cashbackapp.ui.components.InfoPrinterComponent
import mx.diossa.cashbackapp.ui.components.WarningTextComponent
import mx.diossa.cashbackapp.ui.components.exchangeDetails
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen
import mx.diossa.cashbackapp.ui.theme.GreyBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StatusScreen(navController: NavHostController, exchangeViewModel: ExchangeViewModel, viewModel: StatusViewModel = hiltViewModel(), ){
    val exchangeUiState by exchangeViewModel.statusState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(exchangeUiState) {
        exchangeUiState?.let { viewModel.setData(it) }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .padding(bottom = 72.dp)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState())
            ){

                HeaderCenterComponent(uiState.isAvailable)
                HeaderTextComponent(uiState.isAvailable)
                Spacer(modifier = Modifier.height(10.dp))
                if (uiState.isAvailable) {
                    exchangeDetails(
                        idTicket = uiState.idTicket ?: 0,
                        date = uiState.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())),
                        time = uiState.date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        balance = uiState.balance ?: 0
                    )
                } else {
                    val unavailableItems = uiState.unavailableProducts.map { product ->
                        val requestedQuantity = uiState.selectedProducts
                            .firstOrNull { it.product.id == product.id }
                            ?.quantity ?: 0
                        Triple(
                            product.name,
                            "$${"%.2f".format(product.price)}",
                            requestedQuantity.toString()
                        )
                    }

                    if (unavailableItems.isNotEmpty()) {
                        InfoCardNotInventoryComponent(unavailableItems)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                if(uiState.isPrinterConnected) { InfoPrinterComponent() }

                if(!uiState.isAvailable) WarningTextComponent()

                Spacer(modifier = Modifier.height(6.dp))

                if(uiState.isAvailable) { bottomButtonsSucess(navController, viewModel) } else { bottomButtonsFailed(navController) }
            }
        }
    }
}


@Composable
fun bottomButtonsSucess(navController: NavHostController, viewModel: StatusViewModel){
    ButtonsBottomComponent(
        onSelect = { viewModel.reprintTicket() },
        icon = Icons.Outlined.Print,
        iconColor = Color.White,
        colorTextButton = Color.White,
        action = "Reimprimir Nota",
        colorButton = Color.Red
    )

    Spacer(modifier = Modifier.height(8.dp))

    ButtonsBottomComponent(
        onSelect = {
            navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        },
        icon = Icons.Outlined.Home,
        iconColor = TextGreyComponent,
        colorTextButton = TextGreyComponent,
        action = "Volver a Inicio",
        colorButton = GreyBackgroundComponent
    )
}

@Composable
fun bottomButtonsFailed(navController: NavHostController){
    ButtonsBottomComponent(
        onSelect = { navController.popBackStack() },
        icon = Icons.Outlined.ArrowBackIosNew,
        iconColor = Color.White,
        colorTextButton = Color.White,
        action = "Volver a Selección de Productos",
        colorButton = Color.Red
    )

    Spacer(modifier = Modifier.height(8.dp))

    ButtonsBottomComponent(
        onSelect = {
            navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        },
        icon = Icons.Outlined.Home,
        iconColor = TextGreyComponent,
        colorTextButton = TextGreyComponent,
        action = "Volver a Inicio",
        colorButton = GreyBackgroundComponent
    )
}

