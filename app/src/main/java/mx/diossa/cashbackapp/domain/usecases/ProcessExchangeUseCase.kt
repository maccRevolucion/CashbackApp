package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.LoadData
import mx.diossa.cashbackapp.data.remote.dto.LoadItemDetails
import mx.diossa.cashbackapp.data.repository.CashbackRepository
import mx.diossa.cashbackapp.data.repository.InventoryRepository
import mx.diossa.cashbackapp.domain.model.Product
import javax.inject.Inject

sealed class ExchangeResult {
    object Success : ExchangeResult()
    data class InventoryFailure(val unavailableProducts: List<Product>) : ExchangeResult()
    data class ApiError(val message: String) : ExchangeResult()
    data class CarryLimitExceeded(val excessAmount: Double) : ExchangeResult()
}

class ProcessExchangeUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val cashbackRepository: CashbackRepository,
    private val carryUseCase: CarryUseCase,
    private val remoteDataSource: mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
) {
    suspend operator fun invoke(
        ticket: CashbackDetail,
        selectedProducts: Map<Product, Int>
    ): ExchangeResult {
        val MAX_CARRY_ALLOWED = 10.0  // Límite de cashback sobrante
        android.util.Log.d("PROCESSEXCHANGE_USE_CASE", "Iniciando verificación de inventario...")

        val inventoryResult = inventoryRepository.getInventory()
        if (inventoryResult.isFailure) {
            android.util.Log.e("PROCESSEXCHANGE_USE_CASE", "Error obteniendo inventario", inventoryResult.exceptionOrNull())
            return ExchangeResult.ApiError("No se pudo verificar el inventario.")
        }

        val currentInventory = inventoryResult.getOrThrow().associateBy { it.id }
        android.util.Log.d("PROCESSEXCHANGE_USE_CASE", "Inventario actual: $currentInventory")

        val unavailableProducts = selectedProducts.keys.filter {
            (currentInventory[it.id]?.quantity ?: 0) < (selectedProducts[it] ?: 0)
        }

        if (unavailableProducts.isNotEmpty()) {
            android.util.Log.w("PROCESSEXCHANGE_USE_CASE", "Productos sin inventario: $unavailableProducts")
            return ExchangeResult.InventoryFailure(unavailableProducts)
        }

        // MODO MOCK: Descontar del inventario ficticio y saltar llamadas API
        selectedProducts.forEach { (product, quantity) ->
            remoteDataSource.decrementInventory(product.id.toInt(), quantity)
        }

        android.util.Log.d("PROCESSEXCHANGE_USE_CASE", "Inventario actualizado y saltando llamadas a API")
        return ExchangeResult.Success
    }
}