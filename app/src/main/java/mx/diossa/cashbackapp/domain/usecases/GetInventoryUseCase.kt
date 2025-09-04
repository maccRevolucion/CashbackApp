package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.repository.InventoryRepository
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository
){
    suspend operator fun invoke() = inventoryRepository.getInventory()
}