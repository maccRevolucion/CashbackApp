package mx.diossa.cashbackapp.core.utils

class ApiException(
    val code: Int,
    val detail: String
) : Exception(detail)