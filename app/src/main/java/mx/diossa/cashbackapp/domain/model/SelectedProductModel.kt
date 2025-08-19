package mx.diossa.cashbackapp.domain.model

data class SelectedProduct(
    val name: String,
    val price: Double,
    val quantity: Int
)

data class UiStateSelectedProduct(
    val selectedProducts: List<SelectedProduct> = emptyList(),
    val total: Double = 0.0,
    val date: String = ""
)