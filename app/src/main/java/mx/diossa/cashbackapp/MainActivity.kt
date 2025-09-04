package mx.diossa.cashbackapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.diossa.cashbackapp.ui.features.login.LoginScreen
import mx.diossa.cashbackapp.ui.features.navegation.MainScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
            val isLoggedIn = remember {
                mutableStateOf(sharedPreferences.getBoolean("is_logged_in", false))
            }

            if(isLoggedIn.value){
                MainScreen(onLogout = { isLoggedIn.value = false })
            } else{
                LoginScreen(onLoginSuccess = { isLoggedIn.value = true })
            }
        }
    }
}