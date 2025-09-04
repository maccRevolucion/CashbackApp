package mx.diossa.cashbackapp.ui.features.exchange.products


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.domain.model.SelectedProduct
import mx.diossa.cashbackapp.domain.model.Ticket
import mx.diossa.cashbackapp.domain.usecases.GetInventoryUseCase
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

data class UiStateProduct(
    val isLoading: Boolean = true,
    val totalBalance: Int = 0,
    val ticketData: Ticket? = null,
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val selectedQuantities: Map<Product, Int> = emptyMap(),
    val query: String = "",
    val error: String? = null,
    val showSelectionAlert: Boolean = false
)

sealed class ProductNavigationEvent {
    object NavigateToConfirm: ProductNavigationEvent()
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getInventoryUseCase: GetInventoryUseCase,
    //private val exchangeViewModel: ExchangeViewModel,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(UiStateProduct())
    val uiState: StateFlow<UiStateProduct> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<ProductNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        _uiState.update { it.copy(isLoading = false) }
    }

    fun initTicketData(totalBalance: Int) {
        _uiState.update { it.copy(totalBalance = totalBalance) }
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d("ProductsVM", "Iniciando loadProducts...")

            getInventoryUseCase().onSuccess { products ->
                Log.d("ProductsVM", "Éxito: Se recibieron ${products.size} productos del repositorio.")
                if (products.isEmpty()) {
                    Log.w("ProductsVM", "La lista de productos está vacía, se mostrará un mensaje.")
                    _uiState.update { it.copy(isLoading = false, error = "No hay productos disponibles para canjear.") }
                } else {
                    _uiState.update { it.copy(isLoading = false, allProducts = products, filteredProducts = products) }
                }
            }.onFailure { error ->
                Log.e("ProductsVM", "Fallo: ${error.message}")
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        val filtered = _uiState.value.allProducts.filter { product ->
            product.id.contains(newQuery, ignoreCase = true) ||
                    product.name.contains(newQuery, ignoreCase = true)
        }
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                filteredProducts = filtered
            )
        }
    }

    fun getSelectedAmount(): Double = _uiState.value.selectedQuantities.entries.sumOf { (product, quantity) -> product.price * quantity }
    fun getRemaining(): Double = _uiState.value.totalBalance - getSelectedAmount()

    fun onIncrement(product: Product) {
        val currentQuantity = _uiState.value.selectedQuantities[product] ?: 0
        val newSelectedAmount = getSelectedAmount() + product.price
        if (newSelectedAmount <= _uiState.value.totalBalance) {
            _uiState.update { current ->
                current.copy(
                    selectedQuantities = current.selectedQuantities.toMutableMap().apply {
                        put(product, currentQuantity + 1)
                    }
                )
            }
        }
    }

    fun onDecrement(product: Product) {
        val currentQuantity = _uiState.value.selectedQuantities[product] ?: 0
        if (currentQuantity > 0) {
            _uiState.update { current ->
                current.copy(
                    selectedQuantities = current.selectedQuantities.toMutableMap().apply {
                        put(product, currentQuantity - 1)
                        if (get(product) == 0) remove(product)
                    }
                )
            }
        }
    }

    fun onContinueClicked(exchangeViewModel: ExchangeViewModel) {
        if (_uiState.value.selectedQuantities.isEmpty()) {
            _uiState.update { it.copy(showSelectionAlert = true) }
            return
        }
        exchangeViewModel.setSelectedProducts(_uiState.value.selectedQuantities)

        viewModelScope.launch {
            _navigationEvent.emit(ProductNavigationEvent.NavigateToConfirm)
        }
    }

    fun onAlertDismissed() {
        _uiState.update { it.copy(showSelectionAlert = false) }
    }
}