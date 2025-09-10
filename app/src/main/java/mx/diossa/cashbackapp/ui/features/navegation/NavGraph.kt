package mx.diossa.cashbackapp.ui.features.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.exchange.products.ProductScreen
import mx.diossa.cashbackapp.ui.features.history.HistoryScreen
import mx.diossa.cashbackapp.ui.features.menu.MenuScreen
import mx.diossa.cashbackapp.ui.features.printer.PrinterScreen
import mx.diossa.cashbackapp.ui.features.exchange.ticketCheck.TicketCheckScreen
import mx.diossa.cashbackapp.ui.features.scan.ScanScreen
import mx.diossa.cashbackapp.ui.features.exchange.confirm.ConfirmScreen
import mx.diossa.cashbackapp.ui.features.exchange.status.StatusScreen

@Composable
fun NavGraph(navController: NavHostController, onLogout: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) { MenuScreen(navController, onLogout = onLogout) }
        composable(route = Screen.Scan.route) { ScanScreen(navController) }
        composable(route = Screen.History.route) { HistoryScreen(navController) }
        composable(route = Screen.Printer.route) { PrinterScreen(navController) }

        navigation(
            startDestination = Screen.TicketCheck.route,
            route = "exchange_flow"
        ) {
            composable(
                route = Screen.TicketCheck.route + "/{cashbackJson}/{isValid}",
                arguments = listOf(
                    navArgument("cashbackJson") { type = NavType.StringType },
                    navArgument("isValid") { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("exchange_flow")
                }
                val exchangeViewModel: ExchangeViewModel = hiltViewModel(parentEntry)
                TicketCheckScreen(navController, exchangeViewModel)
            }

            composable(route = Screen.Products.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("exchange_flow")
                }
                val exchangeViewModel: ExchangeViewModel = hiltViewModel(parentEntry)
                ProductScreen(navController, exchangeViewModel)
            }


            composable(route = Screen.ConfirmCheck.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("exchange_flow")
                }
                val exchangeViewModel: ExchangeViewModel = hiltViewModel(parentEntry)
                ConfirmScreen(navController, exchangeViewModel)
            }
        }

        composable(route = Screen.StatusExchange.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("exchange_flow")
            }
            val exchangeViewModel: ExchangeViewModel = hiltViewModel(parentEntry)
            StatusScreen(navController, exchangeViewModel)
        }

    }
}

sealed class Screen(val route: String) {
    object Home : Screen("menu")
    object TicketCheck : Screen("ticket")
    object Scan : Screen("scan")
    object History : Screen("history")
    object Printer : Screen("printer")
    object Products : Screen("products")
    object ConfirmCheck: Screen ("confirm")
    object StatusExchange: Screen("exchange")
}