package mx.diossa.cashbackapp.ui.features.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mx.diossa.cashbackapp.ui.components.buttonComponent
import mx.diossa.cashbackapp.ui.components.HeadingTextComponent
import mx.diossa.cashbackapp.ui.components.MyTextFieldComponent
import mx.diossa.cashbackapp.ui.components.NormalTextComponent
import mx.diossa.cashbackapp.ui.components.PasswordTextFieldComponent

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, viewModel: LoginViewModel = hiltViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()
    val context = LocalContext.current
    val ui = uiState.value

    LaunchedEffect(ui.errorMessage) {
        ui.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit){
        viewModel.errorEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(navigationEvent){
        when (navigationEvent){
            LoginViewModel.NavigationEvent.NavigateToMenu ->{
                onLoginSuccess()
                viewModel.clearNavigationEvent()
            }
            null -> {}
        }
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            WelcomeUser()
            Spacer(modifier = Modifier.height(25.dp))
            TextFieldsLogin(
                username = uiState.value.username,
                password = uiState.value.password,
                onUsernameChange = {viewModel.onUsernameChanged(it)},
                onPasswordChange = {viewModel.onPasswordChanged(it)},
                clearTextQuery = {viewModel.clearTextQuery(it)}
            )
            buttonComponent(
                onClick = {viewModel.onLoginClicked()},
                isLoading = uiState.value.isLoading
            )
        }
    }
}

@Composable
fun WelcomeUser(){
    Column {
        NormalTextComponent(value = "Hola de vuelta")
        HeadingTextComponent(value = "Bienvenido 🎉")
    }
}

@Composable
fun TextFieldsLogin(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    clearTextQuery: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
    Column {
        MyTextFieldComponent(
            textValue = username,
            onValueChange = onUsernameChange,
            labelValue = "Usuario",
            icon = Icons.Outlined.Person,
            clearTextQuery = clearTextQuery,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextFieldComponent(
            value = password,
            onValueChange = onPasswordChange,
            labelValue = "Contraseña",
            icon = Icons.Outlined.Lock,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.focusRequester(passwordFocusRequester)
        )
    }
}