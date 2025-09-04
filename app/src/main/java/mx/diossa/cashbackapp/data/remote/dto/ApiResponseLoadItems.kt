package mx.diossa.cashbackapp.data.remote.dto

data class PostLoadItemsRequest(
    val loadData: LoadData
)

data class LoadData(
    val id: Int
    // Otros
)

data class ApiResponseLoadItems(
    val success: Boolean,
    val data: LoadResponse?,
    val error: String?
)

data class LoadResponse(
    val status: String
    // Otros
)