package mx.diossa.cashbackapp.ui.features.exchange.products

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.domain.model.UiStateProduct
import javax.inject.Inject

class ProductsViewModel @Inject constructor(): ViewModel(){
    private val allProducts = listOf(
        Product("A1", "Producto A", 30.00),
        Product("B1", "Producto B", 250.00),
        Product("C1", "Producto C", 150.00)
        // Agrega más productos si es necesario
    )

    private val _uiState = MutableStateFlow(UiStateProduct(filteredProducts = allProducts))
    val uiState: StateFlow<UiStateProduct> = _uiState.asStateFlow()

    fun getSelectedAmount(): Double {
        return _uiState.value.selectedQuantities.entries.sumOf { entry ->
            entry.key.price * entry.value
        }
    }

    fun getRemaining(): Double {
        return _uiState.value.totalBalance - getSelectedAmount()
    }

    fun onQueryChanged(newQuery: String) {
        val filtered = allProducts.filter { product ->
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
}