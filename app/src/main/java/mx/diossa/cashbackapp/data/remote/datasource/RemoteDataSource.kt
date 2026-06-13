package mx.diossa.cashbackapp.data.remote.datasource

import mx.diossa.cashbackapp.data.remote.api.ApiService
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseCarry
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseCashback
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseCashbackDetails
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseInventory
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLoadItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseLogin
import mx.diossa.cashbackapp.data.remote.dto.ApiResponsePostItems
import mx.diossa.cashbackapp.data.remote.dto.ApiResponseUpdateStatus
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.data.remote.dto.EmployeeCarryData
import mx.diossa.cashbackapp.data.remote.dto.ItemData
import mx.diossa.cashbackapp.data.remote.dto.LoadData
import mx.diossa.cashbackapp.data.remote.dto.LoadItemDetails
import mx.diossa.cashbackapp.data.remote.dto.UpdateStatusRequest
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest
import mx.diossa.cashbackapp.data.remote.dto.LoginData
import mx.diossa.cashbackapp.data.remote.dto.InventoryItem
import mx.diossa.cashbackapp.data.remote.dto.PostItemsData
import mx.diossa.cashbackapp.data.remote.dto.PostItemsDetails
import mx.diossa.cashbackapp.data.remote.dto.PostLoadItemsData
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    // Estado persistente en memoria para el inventario mock
    private val mockInventory = mutableMapOf(
        1001 to 99, 1002 to 99, 1003 to 99, 1004 to 99, 1005 to 99,
        1006 to 9, 1007 to 99, 1008 to 99, 1009 to 99, 1010 to 99,
        1011 to 99, 1012 to 99, 1013 to 99, 1014 to 99, 1015 to 99,
        1016 to 99, 1017 to 99, 1018 to 99, 1019 to 99, 1020 to 90,
        1021 to 99, 1022 to 99
    )

    private val productNames = mapOf(
        1001 to "Coca Cola 600ml", 1002 to "Pepsi 600ml", 1003 to "Agua Ciel 1L",
        1004 to "Papas Sabritas Originales", 1005 to "Rufles de Queso", 1006 to "Doritos Nacho",
        1007 to "Gansito Bimbo", 1008 to "Pingüinos Marinela", 1009 to "Galletas Chokis",
        1010 to "Jugo del Valle Durazno", 1011 to "Red Bull 250ml", 1012 to "Monster Energy 473ml",
        1013 to "Galletas Emperador Chocolate", 1014 to "Pan Bimbo Cero Cero", 1015 to "Leche Alpura Semidescremada 1L",
        1016 to "Café Nescafé Clásico 120g", 1017 to "Azúcar Caña 1kg", 1018 to "Aceite Capullo 1L",
        1019 to "Arroz Extra Verde Valle 1kg", 1020 to "Frijol Negro Isadora 430g", 1021 to "Atún Herdez en Agua 130g",
        1022 to "Sopa Maruchan Pollo"
    )

    private val productPrices = mapOf(
        1001 to 18.0, 1002 to 17.0, 1003 to 12.0, 1004 to 20.0, 1005 to 21.0,
        1006 to 22.0, 1007 to 15.0, 1008 to 19.0, 1009 to 16.5, 1010 to 14.0,
        1011 to 45.0, 1012 to 48.0, 1013 to 18.5, 1014 to 49.5, 1015 to 26.0,
        1016 to 85.0, 1017 to 32.0, 1018 to 42.0, 1019 to 35.0, 1020 to 18.0,
        1021 to 21.0, 1022 to 15.0
    )

    fun decrementInventory(productId: Int, quantity: Int) {
        val current = mockInventory[productId] ?: 0
        mockInventory[productId] = (current - quantity).coerceAtLeast(0)
    }

    suspend fun login(request: LoginRequest): ApiResponseLogin {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponseLogin(
            success = true,
            data = LoginData(
                idEmployee = 1,
                username = request.username,
                employeeName = "Manuel Cordero",
                idCedis = 101,
                nameCedis = "CEDIS Central",
                access = "mock_access_token",
                refresh = "mock_refresh_token"
            ),
            error = null
        )
    }

    suspend fun getInventory(): ApiResponseInventory {
        kotlinx.coroutines.delay(500.milliseconds)
        val items = mockInventory.map { (id, qty) ->
            InventoryItem(
                idProduct = id,
                nameProduct = productNames[id] ?: "Producto $id",
                unitPrice = productPrices[id] ?: 0.0,
                quantity = qty
            )
        }
        return ApiResponseInventory(
            success = true,
            data = items,
            error = null
        )
    }

    suspend fun getCashbackDetails(idCashback: Int): ApiResponseCashback {
        kotlinx.coroutines.delay(500.milliseconds)
        val today = java.time.LocalDate.now().toString()
        return ApiResponseCashback(
            success = true,
            data = CashbackDetail(
                idCashback = idCashback,
                idRoute = 42,
                nameRoute = "Ruta Milpa Alta",
                idEmployee = 1,
                nameEmployee = "Cliente de Prueba",
                cashbackDate = today,
                cashbackValue = 1500,
                objectiveType = "VENTA",
                status = "PENDING"
            ),
            error = null
        )
    }

    suspend fun updateStatus(request: UpdateStatusRequest): ApiResponseUpdateStatus {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponseUpdateStatus(
            success = true,
            data = "Status updated to ${request.status}",
            error = null
        )
    }

    suspend fun postItems(request: List<ItemData>): ApiResponsePostItems {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponsePostItems(
            success = true,
            data = PostItemsData(idProduct = request.firstOrNull()?.idProduct ?: 0),
            error = null
        )
    }

    suspend fun postItemDetails(idCashback: Int, request: List<LoadItemDetails>): ApiResponseCashbackDetails {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponseCashbackDetails(
            success = true,
            data = PostItemsDetails(idProduct = request.firstOrNull()?.idProduct ?: 0),
            error = null
        )
    }

    suspend fun postLoadItems(request: List<LoadData>): ApiResponseLoadItems {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponseLoadItems(
            success = true,
            data = PostLoadItemsData(idProduct = request.firstOrNull()?.idProduct ?: 0),
            error = null
        )
    }

    suspend fun postCarry(request: EmployeeCarryData): ApiResponseCarry {
        kotlinx.coroutines.delay(500.milliseconds)
        return ApiResponseCarry(
            success = true,
            data = EmployeeCarryData(
                employeeId = request.employeeId,
                quantity = request.quantity
            ),
            error = null
        )
    }
}