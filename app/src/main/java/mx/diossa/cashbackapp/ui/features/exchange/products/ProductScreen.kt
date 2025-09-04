package mx.diossa.cashbackapp.ui.features.exchange.products

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.domain.model.SelectedProduct
import mx.diossa.cashbackapp.domain.model.Ticket
import mx.diossa.cashbackapp.ui.components.HeaderTitleProductComponent
import mx.diossa.cashbackapp.ui.components.ItemProduct
import mx.diossa.cashbackapp.ui.components.SearchBar
import mx.diossa.cashbackapp.ui.components.SubHeader
import mx.diossa.cashbackapp.ui.components.bottomButton
import mx.diossa.cashbackapp.ui.components.inforCard
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen

@Composable
fun ProductScreen(
    navController: NavHostController,
    exchangeViewModel: ExchangeViewModel
){
    val viewModel: ProductsViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        exchangeViewModel.exchangeState.value.ticketDetails?.let {
            viewModel.initTicketData(it.cashbackValue)
        }
    }

    val navEvent = viewModel.navigationEvent.collectAsState(initial = null)
    LaunchedEffect(navEvent.value) {
        if (navEvent.value is ProductNavigationEvent.NavigateToConfirm) {
            navController.navigate(Screen.ConfirmCheck.route)
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
                HeaderTitleProductComponent(onBack = { navController.popBackStack() })
                Spacer(modifier = Modifier.height(18.dp))
                inforCard(
                    balance = uiState.totalBalance,
                    selected = viewModel.getSelectedAmount(),
                    remaining = viewModel.getRemaining()
                )
                Spacer(modifier = Modifier.height(10.dp))
                SubHeader()
                Spacer(modifier = Modifier.height(8.dp))
                SearchBar(
                    query = uiState.query,
                    onQueryChange = viewModel::onQueryChanged,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    uiState.error != null -> {
                        Text(
                            text = uiState.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                    else -> {
                        uiState.filteredProducts.forEach { product ->
                            ItemProduct(
                                product = product,
                                quantity = uiState.selectedQuantities[product] ?: 0,
                                onIncrement = { viewModel.onIncrement(product) },
                                onDecrement = { viewModel.onDecrement(product) }
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                bottomButton(onSelected = {viewModel.onContinueClicked(exchangeViewModel)})
            }
        }
    }
}