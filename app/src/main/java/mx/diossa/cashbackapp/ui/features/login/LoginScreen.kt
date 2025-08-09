package mx.diossa.cashbackapp.ui.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ButtomComponent
import mx.diossa.cashbackapp.ui.components.HeadingTextComponent
import mx.diossa.cashbackapp.ui.components.MyTextFieldComponent
import mx.diossa.cashbackapp.ui.components.NormalTextComponent
import mx.diossa.cashbackapp.ui.components.PasswordTextFieldComponent

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()){
    val uiState = viewModel.uiState.collectAsState()

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
                onPasswordChange = {viewModel.onPasswordChanged(it)}
            )
            ButtomComponent(
                navController,
                enable = uiState.value.isButtonEnabled,
                onClick = {viewModel.onLoginClicked(navController)},
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
    onPasswordChange: (String) -> Unit
) {
    Column {
        MyTextFieldComponent(
            textValue = username,
            onValueChange = onUsernameChange,
            labelValue = "Usuario",
            icon = Icons.Outlined.Person
        )

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextFieldComponent(
            value = password,
            onValueChange = onPasswordChange,
            labelValue = "Contraseña",
            icon = Icons.Outlined.Lock
        )
    }
}