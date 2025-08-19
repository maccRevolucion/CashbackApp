package mx.diossa.cashbackapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.diossa.cashbackapp.ui.theme.GreenBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.GreenCompletedComponent
import mx.diossa.cashbackapp.ui.theme.Grey20
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent
import mx.diossa.cashbackapp.ui.theme.Grey95
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.RedBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.RedTextComponent
import mx.diossa.cashbackapp.ui.theme.SecondaryColor
import mx.diossa.cashbackapp.ui.theme.TextColor

@Preview(showBackground = true)
@Composable
fun HeaderTitle(){
    Row(modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Configuración de Impresora",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = TextColor,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun statusConnectFailedPrinterComponent(){
    Text(
        text = "Desconectado",
        color = RedTextComponent,
        modifier = Modifier
            .background(RedBackgroundComponent, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}
@Preview
@Composable
fun statusConnectSuccesPrinterComponent(){
    Text(
        text = "Conectada",
        color = GreenCompletedComponent,
        modifier = Modifier
            .background(GreenBackgroundComponent, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Composable
fun PrinterDataComponent(printerName: String, isConnected: Boolean){
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .width(380.dp)
            .height(180.dp),
        shape  = RectangleShape,
        color = Grey95,
        tonalElevation = 2.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ){
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(40.dp),
                    shape = CircleShape,
                    color = SecondaryColor,
                    tonalElevation = 2.dp,
                ) {
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        imageVector = Icons.Outlined.Print,
                        contentDescription = "",
                        tint = PrimaryColor
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.Top)
                ) {
                    Text(
                        text = printerName,
                        modifier = Modifier,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = "Impresora predeterminada",
                        textAlign = TextAlign.Start,
                        color = Grey20,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth())
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.height(35.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        color = TextGreyComponent,
                        text = "Estado:"
                    )
                    if(isConnected){
                        statusSuccessPrinterComponent()
                    } else {
                        statusFailPrinterComponent()
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        color = TextGreyComponent,
                        text = "Tamaño del papel:"
                    )
                    Text(text = "80mm")
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        color = TextGreyComponent,
                        text = "Tipo de conexión:"
                    )
                    Text(text = "Bluetooth")
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun statusFailPrinterComponent(){
    Text(
        color = RedTextComponent,
        text = "No disponible"
    )
}
@Preview(showBackground = true)
@Composable
fun statusSuccessPrinterComponent(){
    Text(
        color = GreenCompletedComponent,
        text = "Lista para imprimir"
    )
}

@Preview
@Composable
fun actionButtonsComponent(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        OutlinedButton(
            onClick = {  },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height( 40.dp ),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            Icon(
                imageVector = Icons.Default.Autorenew,
                contentDescription = "Actualizar",
                tint = TextGreyComponent
            )
            Text(
                text = "Actualizar",
                color = TextGreyComponent,
                modifier = Modifier.padding(start = 8.dp)
            )
        }


        Button(
            onClick = { },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height( 40.dp ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            Icon(
                imageVector = Icons.Default.Print,
                contentDescription = "Imprimir Prueba",
                tint = Color.White
            )
            Text(
                text = "Imprimir Prueba",
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun printerStatus(printerName: String, isConnected: Boolean){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Estado de la Impresora",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
            )
            if(isConnected){
                statusConnectSuccesPrinterComponent()
            } else {
                statusConnectFailedPrinterComponent()
            }
        }

        PrinterDataComponent(printerName, isConnected)
        actionButtonsComponent()
    }
}

@Composable
fun PrinterConfig(
    printerName: String,
    macAddress: String,
    onNameChanged: (String) -> Unit,
    onMacChanged: (String) -> Unit,
    onSave: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Configuración",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Nombre de la impresora
                Text("Nombre de la impresora")
                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    value = printerName,
                    onValueChange = { onNameChanged(it) },
                    placeholder = { Text("Ej. PrinterBT") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Dirección MAC
                Text("Dirección MAC de la impresora")
                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    value = macAddress,
                    onValueChange = { onMacChanged((it)) },
                    placeholder = { Text("Ej. 00:11:22:33:44:55") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                //Boton de Guardado
                Button(
                    onClick = { onSave(printerName, macAddress) },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Text(
                        color = Color.White,
                        text = "Guardar Configuración"
                    )
                }
            }
        }
    }
}