package mx.diossa.cashbackapp.data.repository

import android.util.Log
import mx.diossa.cashbackapp.data.remote.datasource.RemoteDataSource
import mx.diossa.cashbackapp.domain.model.Product
import mx.diossa.cashbackapp.data.remote.dto.toDomain

import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    suspend fun getInventory(): Result<List<Product>> {
        return try {
            Log.d("InventoryRepo", "Iniciando llamada a getInventory...")
            val response = remoteDataSource.getInventory()
            Log.d("InventoryRepo", "Respuesta de API recibida. Success: ${response.success}")

            if (response.success && !response.data.isNullOrEmpty()) {
                Log.d("InventoryRepo", "API devolvió ${response.data.size} items.")

                val availableProducts = response.data
                    .filter { it.quantity > 0 }
                    .map { it.toDomain() }

                Log.d("InventoryRepo", "Procesados ${availableProducts.size} productos disponibles. Mapeo exitoso.")
                Result.success(availableProducts)
            } else {
                val errorMessage = response.error ?: "La API no devolvió productos."
                Log.w("InventoryRepo", "Fallo o datos vacíos: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("InventoryRepo", "Excepción en la llamada de red: ${e.message}", e)
            Result.failure(e)
        }
    }
}