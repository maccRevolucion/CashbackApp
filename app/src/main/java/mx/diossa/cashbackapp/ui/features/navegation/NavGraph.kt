package mx.diossa.cashbackapp.ui.features.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.diossa.cashbackapp.ui.features.exchange.products.ProductScreen
import mx.diossa.cashbackapp.ui.features.history.HistoryScreen
import mx.diossa.cashbackapp.ui.features.login.LoginScreen
import mx.diossa.cashbackapp.ui.features.menu.MenuScreen
import mx.diossa.cashbackapp.ui.features.printer.PrinterScreen
import mx.diossa.cashbackapp.ui.features.exchange.ticketCheck.TicketCheckScreen
import mx.diossa.cashbackapp.ui.features.scan.ScanScreen
import mx.diossa.cashbackapp.ui.features.exchange.confirm.confirmScreen
import mx.diossa.cashbackapp.ui.features.exchange.status.StatusScreen

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
        composable(route = Screen.Products.route){ ProductScreen(navController) }
        composable(route = Screen.ConfirmCheck.route){ confirmScreen(navController) }
        composable(route = Screen.StatusExchange.route){ StatusScreen(navController) }
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