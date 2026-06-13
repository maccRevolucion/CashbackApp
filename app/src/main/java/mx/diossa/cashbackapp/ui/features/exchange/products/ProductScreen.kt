package mx.diossa.cashbackapp.ui.features.exchange.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.*
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen

@Composable
fun ProductScreen(
    navController: NavHostController,
    exchangeViewModel: ExchangeViewModel
){
    val viewModel: ProductsViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val listState = rememberLazyListState()

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
                    .padding(horizontal = 8.dp)
            ) {
                HeaderTitleProductComponent(onBack = { navController.popBackStack() })
                Spacer(modifier = Modifier.height(18.dp))
                InforCard(
                    balance = uiState.totalBalance,
                    selected = viewModel.getSelectedAmount(),
                    remaining = viewModel.getRemaining()
                )
                Spacer(modifier = Modifier.height(10.dp))
                WarningCashback()
                Spacer(modifier = Modifier.height(5.dp))
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

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    val productsToShow = if (uiState.query.isNotEmpty()) {
                        uiState.filteredProducts
                    } else {
                        uiState.visibleProducts
                    }

                    itemsIndexed(productsToShow) { index, product ->
                        ItemProduct(
                            product = product,
                            quantity = uiState.selectedQuantities[product] ?: 0,
                            onIncrement = { viewModel.onIncrement(product) },
                            onDecrement = { viewModel.onDecrement(product) }
                        )

                        if (uiState.query.isEmpty() &&
                            index >= uiState.visibleProducts.size - 1 &&
                            !uiState.endReached &&
                            !uiState.isLoading
                        ) {
                            viewModel.loadNextPage()
                        }
                    }
                    if (uiState.isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                    if (uiState.error != null) {
                        item {
                            Text(
                                text = uiState.error,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                color = Color.Gray
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
                BottomButton(onSelected = { viewModel.onContinueClicked(exchangeViewModel) })
            }
        }
    }
}
