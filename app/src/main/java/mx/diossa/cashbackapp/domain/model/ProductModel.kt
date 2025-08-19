package mx.diossa.cashbackapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double
)

data class UiStateProduct(
    val query: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val selectedQuantities: Map<Product, Int> = emptyMap(),
    val totalBalance: Double = 500.0
)