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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.ButtonsBottomComponent
import mx.diossa.cashbackapp.ui.components.HeaderIconComponent
import mx.diossa.cashbackapp.ui.components.HeadingTextComponent
import mx.diossa.cashbackapp.ui.components.InfoPrinterComponent
import mx.diossa.cashbackapp.ui.components.exchangeDetails
import mx.diossa.cashbackapp.ui.theme.GreyBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent

@Composable
fun StatusScreen(navController: NavHostController){
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
                HeaderCenterComponent()
                HeadingTextComponent()
                Spacer(modifier = Modifier.height(10.dp))
                exchangeDetails()
                Spacer(modifier = Modifier.height(6.dp))
                InfoPrinterComponent()
                Spacer(modifier = Modifier.height(6.dp))

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
        }
    }
}

@Composable
fun HeaderCenterComponent(){
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        HeaderIconComponent()
    }
}