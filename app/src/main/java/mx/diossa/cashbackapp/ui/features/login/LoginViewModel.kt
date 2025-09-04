package mx.diossa.cashbackapp.ui.features.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.domain.model.LoginUserModel
import mx.diossa.cashbackapp.domain.usecases.ValidateLoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUserModel())
    val uiState: StateFlow<LoginUserModel> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    sealed class NavigationEvent{
        object NavigateToMenu : NavigationEvent()
    }

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
        val isPasswordValid = password.isNotBlank()
        return isUsernameValid && isPasswordValid
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = validateLoginUseCase(_uiState.value.username.trim(), _uiState.value.password.trim())

            result.onSuccess { loginResult ->
                sharedPreferences.edit()
                    .putBoolean("is_logged_in", true)
                    .putString("access_token", loginResult.access)
                    .putString("employee_name", loginResult.employeeName)
                    .apply()

                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.value = NavigationEvent.NavigateToMenu

            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Credenciales inválidas"
                    )
                }
            }
        }
    }

    fun clearTextQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = "",
                isButtonEnabled = isFormValid("", currentState.password)
            )
        }
    }

    fun clearNavigationEvent(){
        _navigationEvent.value = null
    }

}