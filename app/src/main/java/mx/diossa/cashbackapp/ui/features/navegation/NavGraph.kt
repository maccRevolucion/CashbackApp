package mx.diossa.cashbackapp.ui.features.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.diossa.cashbackapp.ui.features.exchange.products.ProductScreen
import mx.diossa.cashbackapp.ui.features.history.HistoryScreen
import mx.diossa.cashbackapp.ui.features.login.LoginScreen
import mx.diossa.cashbackapp.ui.features.menu.MenuScreen
import mx.diossa.cashbackapp.ui.features.printer.PrinterScreen
import mx.diossa.cashbackapp.ui.features.exchange.ticketCheck.TicketCheckScreen
import mx.diossa.cashbackapp.ui.features.scan.ScanScreen
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmScreen
import mx.diossa.cashbackapp.ui.features.exchange.status.StatusScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) { MenuScreen(navController) }
        composable(route = Screen.Sales.route) { TicketCheckScreen(navController) }
        composable(route = Screen.Scan.route) { ScanScreen(navController) }
        composable(route = Screen.History.route) { HistoryScreen(navController) }
        composable(route = Screen.Printer.route) { PrinterScreen(navController) }
        composable(route = Screen.Products.route){ ProductScreen(navController) }
        composable(route = Screen.ConfirmCheck.route){ ConfirmScreen(navController) }
        composable(route = Screen.StatusExchange.route){ StatusScreen(navController) }
        composable(
            route = "confirm/{selected}",
            arguments = listOf(navArgument("selected") { type = NavType.StringType })
        ) { backStackEntry ->
            val selected = backStackEntry.arguments?.getString("selected") ?: ""
            ConfirmScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("menu")
    object Sales : Screen("sales")
    object Scan : Screen("scan")
    object History : Screen("history")
    object Printer : Screen("printer")
    object Products : Screen("products")
    object ConfirmCheck: Screen ("confirm")
    object StatusExchange: Screen("exchange")
}