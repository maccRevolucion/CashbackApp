package mx.diossa.cashbackapp.presentation.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.diossa.cashbackapp.presentation.exchange.products.ProductScreen
import mx.diossa.cashbackapp.presentation.history.HistoryScreen
import mx.diossa.cashbackapp.presentation.login.LoginScreen
import mx.diossa.cashbackapp.presentation.menu.MenuScreen
import mx.diossa.cashbackapp.presentation.printer.PrinterScreen
import mx.diossa.cashbackapp.presentation.exchange.ticketCheck.TicketCheckScreen
import mx.diossa.cashbackapp.presentation.scan.ScanScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) { LoginScreen(navController) }
        composable(route = Screen.Home.route) { MenuScreen(navController) }
        composable(route = Screen.Sales.route) { TicketCheckScreen(navController) }
        composable(route = Screen.Scan.route) { ScanScreen(navController) }
        composable(route = Screen.History.route) { HistoryScreen(navController) }
        composable(route = Screen.Printer.route) { PrinterScreen(navController) }
        composable(route = Screen.Products.route){ ProductScreen(navController)}
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("menu")
    object Sales : Screen("sales")
    object Scan : Screen("scan")
    object History : Screen("history")
    object Printer : Screen("printer")
    object Products : Screen("products")
    object ConfirmCheck: Screen ("confirm")
    object StatusExchange: Screen("exchange")
}