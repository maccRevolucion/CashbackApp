package mx.diossa.cashbackapp.ui.features.exchange.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ButtonsBottomComponent
import mx.diossa.cashbackapp.ui.components.HeaderCenterComponent
import mx.diossa.cashbackapp.ui.components.HeaderIconComponent
import mx.diossa.cashbackapp.ui.components.HeaderTextComponent
import mx.diossa.cashbackapp.ui.components.HeadingTextComponent
import mx.diossa.cashbackapp.ui.components.InfoCardNotInventoryComponent
import mx.diossa.cashbackapp.ui.components.InfoNoPrinterComponent
import mx.diossa.cashbackapp.ui.components.InfoPrinterComponent
import mx.diossa.cashbackapp.ui.components.WarningTextComponent
import mx.diossa.cashbackapp.ui.components.exchangeDetails
import mx.diossa.cashbackapp.ui.theme.GreyBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent

@Composable
fun StatusScreen(navController: NavHostController, viewModel: StatusViewModel = hiltViewModel()){
    val isAvailable = false
    val isConnected = false

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .padding(bottom = 72.dp)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.Center)
            ){

                HeaderCenterComponent(isAvailable)
                HeaderTextComponent(isAvailable)
                Spacer(modifier = Modifier.height(10.dp))
                if(isAvailable){ exchangeDetails() } else { InfoCardNotInventoryComponent() }
                Spacer(modifier = Modifier.height(6.dp))
                if(isConnected) { InfoPrinterComponent() }
                if(!isAvailable) WarningTextComponent()
                Spacer(modifier = Modifier.height(6.dp))
                if(isAvailable) { bottomButtonsSucess(navController) } else { bottomButtonsFailed(navController) }
            }
        }
    }
}


@Composable
fun bottomButtonsSucess(navController: NavHostController){
    ButtonsBottomComponent(
        onSelect = { /*TODO*/ },
        icon = Icons.Outlined.Print,
        iconColor = Color.White,
        colorTextButton = Color.White,
        action = "Reimprimir Nota",
        colorButton = Color.Red
    )

    Spacer(modifier = Modifier.height(8.dp))

    ButtonsBottomComponent(
        onSelect = { navController.navigate("Menu") },
        icon = Icons.Outlined.Home,
        iconColor = TextGreyComponent,
        colorTextButton = TextGreyComponent,
        action = "Volver a Inicio",
        colorButton = GreyBackgroundComponent
    )
}

@Composable
fun bottomButtonsFailed(navController: NavHostController){
    ButtonsBottomComponent(
        onSelect = { navController.popBackStack() },
        icon = Icons.Outlined.ArrowBackIosNew,
        iconColor = Color.White,
        colorTextButton = Color.White,
        action = "Volver a Selección de Productos",
        colorButton = Color.Red
    )

    Spacer(modifier = Modifier.height(8.dp))

    ButtonsBottomComponent(
        onSelect = { navController.navigate("Menu") },
        icon = Icons.Outlined.Home,
        iconColor = TextGreyComponent,
        colorTextButton = TextGreyComponent,
        action = "Volver a Inicio",
        colorButton = GreyBackgroundComponent
    )
}

