package mx.diossa.cashbackapp.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.diossa.cashbackapp.domain.model.LoginUser
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState: StateFlow<LoginUser> = _uiState.asStateFlow()

    fun onUsernameChanged(username: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = username,
                isButtonEnabled = isFormValid(username, currentState.password)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isButtonEnabled = isFormValid(currentState.username, password)
            )
        }
    }

    private fun isFormValid(username: String, password: String): Boolean {
        val isUsernameValid = username.isNotBlank()
        val isPasswordValid = password.isNotBlank() && password.length >= 6 // Ejemplo: mínimo 6 caracteres
        return isUsernameValid && isPasswordValid
    }

    fun onLoginClicked(navController: NavHostController) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        // Aquí puedes agregar la lógica para autenticar al usuario, por ejemplo:
        // - Llamar a un caso de uso o repositorio para validar credenciales
        // - Manejar éxito o error
        val username = _uiState.value.username
        val password = _uiState.value.password

        if (username == "test" && password == "password123") {
            _uiState.update { it.copy(isLoading = false, errorMessage = null) }
            navController.navigate("Menu")
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Usuario o contraseña incorrectos"
                )
            }
        }
    }
}
