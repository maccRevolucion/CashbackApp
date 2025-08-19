package mx.diossa.cashbackapp.domain.model

data class LoginUserModel(
    val username: String = "",
    val password: String = "",
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)