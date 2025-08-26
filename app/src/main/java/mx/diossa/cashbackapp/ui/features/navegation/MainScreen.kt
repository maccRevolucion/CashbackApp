package mx.diossa.cashbackapp.ui.features.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(currentRoute)) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar(
        modifier = Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        listOf(Screen.Home, Screen.Scan, Screen.History, Screen.Printer).forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen) {
                        Screen.Home    -> Icon(Icons.Filled.Home,    contentDescription = "Inicio")
                        Screen.Scan    -> Icon(Icons.Filled.QrCode2, contentDescription = "Escanear")
                        Screen.History -> Icon(Icons.Filled.History, contentDescription = "Historial")
                        Screen.Printer -> Icon(Icons.Filled.Print,   contentDescription = "Impresora")
                        else -> {}
                    }
                },
                label = {
                    Text(
                        text = screen.route.replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Color(0xFFE3000F),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor   = Color(0xFFE3000F),
                    unselectedTextColor = Color.Gray,
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}


@Composable
fun shouldShowBottomBar(route: String?): Boolean {
    return when (route) {
        Screen.TicketCheck.route -> false
        Screen.Products.route -> false
        Screen.ConfirmCheck.route -> false
        Screen.StatusExchange.route -> false
        "confirm/{selected}" -> false
        else -> true
    }
}