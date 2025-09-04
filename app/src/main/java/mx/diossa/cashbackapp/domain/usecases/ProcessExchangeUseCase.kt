package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.repository.CashbackRepository
import mx.diossa.cashbackapp.data.repository.InventoryRepository
import mx.diossa.cashbackapp.domain.model.Product
import javax.inject.Inject

sealed class ExchangeResult {
    object Success : ExchangeResult()
    data class InventoryFailure(val unavailableProducts: List<Product>) : ExchangeResult()
    data class ApiError(val message: String) : ExchangeResult()
}

class ProcessExchangeUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val cashbackRepository: CashbackRepository
) {
    suspend operator fun invoke(
        ticket: CashbackDetail,
        selectedProducts: Map<Product, Int>
    ): ExchangeResult {
        android.util.Log.d("UseCase", "Iniciando verificación de inventario...")

        val inventoryResult = inventoryRepository.getInventory()
        if (inventoryResult.isFailure) {
            android.util.Log.e("UseCase", "Error obteniendo inventario", inventoryResult.exceptionOrNull())
            return ExchangeResult.ApiError("No se pudo verificar el inventario.")
        }

        val currentInventory = inventoryResult.getOrThrow().associateBy { it.id }
        android.util.Log.d("UseCase", "Inventario actual: $currentInventory")

        val unavailableProducts = selectedProducts.keys.filter {
            (currentInventory[it.id]?.quantity ?: 0) < (selectedProducts[it] ?: 0)
        }

        if (unavailableProducts.isNotEmpty()) {
            android.util.Log.w("UseCase", "Productos sin inventario: $unavailableProducts")
            return ExchangeResult.InventoryFailure(unavailableProducts)
        }

        android.util.Log.d("UseCase", "Inventario OK, actualizando cashback ${ticket.idCashback}")
        val statusResult = cashbackRepository.updateCashbackStatus(ticket.idCashback)
        if (statusResult.isFailure) {
            android.util.Log.e("UseCase", "Error actualizando estado", statusResult.exceptionOrNull())
            return ExchangeResult.ApiError("No se pudo actualizar el estado del ticket.")
        }

        val itemsToPost = selectedProducts.map { (product, quantity) ->
            ItemData(idProduct = product.id.toInt(), quantity = quantity)
        }
        android.util.Log.d("UseCase", "Items a enviar en POST: $itemsToPost")

        val postResult = cashbackRepository.postExchangeItems(itemsToPost)
        if (postResult.isFailure) {
            android.util.Log.e("UseCase", "Error registrando productos", postResult.exceptionOrNull())
            return ExchangeResult.ApiError("No se pudieron registrar los productos canjeados.")
        }

        android.util.Log.d("UseCase", "Proceso de canje completado correctamente")
        return ExchangeResult.Success
    }

}