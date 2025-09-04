package mx.diossa.cashbackapp.domain.model

import mx.diossa.cashbackapp.data.remote.dto.ProductDto

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

data class UiStateProduct(
    val query: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val selectedQuantities: Map<Product, Int> = emptyMap(),
    val totalBalance: Double = 500.0
)

fun ProductDto.toDomain(): Product {
    return Product(
        id = this.productId.toString(),
        name = this.productName,
        price = this.unitPrice,
        quantity = this.quantity
    )
}