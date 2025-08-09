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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.diossa.cashbackapp.ui.theme.BlueBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.BlueComponent
import mx.diossa.cashbackapp.ui.theme.GreenBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.GreenCompletedComponent
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.RedBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.RedTextComponent
import mx.diossa.cashbackapp.ui.theme.TextColor
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent


@Preview
@Composable
fun HeaderIconComponent(){
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .width(60.dp)
            .height(60.dp),
        shape  = CircleShape,
        color = GreenBackgroundComponent,
        tonalElevation = 2.dp,
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            tint = GreenCompletedComponent,
            imageVector = Icons.Outlined.CheckCircleOutline,
            contentDescription = ""
        )
    }
}

@Composable
fun HeadingTextComponent() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "¡Canje Exitoso!",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = TextColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ),
            color = TextGreyComponent,
            textAlign = TextAlign.Center,
            text = "El canje ha sido procesado correctamente y la nota de venta ha sido enviada a la impresora"
        )
    }
}

@Preview
@Composable
fun exchangeDetails(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
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
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                text = "Detalles del Canje"
            )
            Text(
                text = "Completado",
                color = Color.White,
                modifier = Modifier
                    .background(GreenCompletedComponent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
        ItemListExchangeDetailsComponent(concept = "Ticket Original:", value = "T-12345")
        ItemListExchangeDetailsComponent(concept = "Referencia:", value = "R-77386")
        ItemListExchangeDetailsComponent(concept = "Fecha:", value = "26/07/2025")
        ItemListExchangeDetailsComponent(concept = "Hora:", value = "12:12:24 p.m.")

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = TextGreyComponent,
                text = "Total:"
            )
            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                text = "$500.00",
                color = PrimaryColor,
            )
        }
    }
}

@Composable
fun ItemListExchangeDetailsComponent(concept: String, value: String){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = TextGreyComponent,
            text = concept
        )
        Text(
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = Color.Black,
            text = value
        )
    }
}

@Preview
@Composable
fun InfoPrinterComponent(){
    Surface(
        color = BlueBackgroundComponent,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(50.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)){
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    tint = BlueComponent,
                    imageVector = Icons.Outlined.Print,
                    contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = BlueComponent,
                    text = "Documento enviado a la impresora"
                )
            }
        }
    }
}

@Composable
fun ButtonsBottomComponent(onSelect: () -> Unit, icon: ImageVector, iconColor: Color, colorTextButton: Color, action: String, colorButton: Color){
    Button(
        onClick = onSelect,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorButton
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = iconColor
        )
        Text(
            text = action,
            color = colorTextButton,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun HeaderIconNotAvailableComponent(){
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .width(60.dp)
            .height(60.dp),
        shape  = CircleShape,
        color = RedBackgroundComponent,
        tonalElevation = 2.dp,
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            tint = RedTextComponent,
            imageVector = Icons.Outlined.Clear,
            contentDescription = ""
        )
    }
}

@Composable
fun HeadingTextNotAvailableComponent() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "Sin Inventario",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = TextColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ),
            color = TextGreyComponent,
            textAlign = TextAlign.Center,
            text = "Los siguientes productos no están disponibles actualmente"
        )
    }
}

@Preview
@Composable
fun InfoNotInventoryComponent(){
    Surface(
        color = RedBackgroundComponent,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(80.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp),
                color = RedTextComponent,
                textAlign = TextAlign.Center,
                text = "Los siguientes productos no están disponibles en inventario."
            )
        }
    }
}

@Preview
@Composable
fun InfoCardNotInventoryComponent(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            InfoNotInventoryComponent()
            Spacer(modifier = Modifier.height(4.dp))
            ProductNotAvailableCardComponent()
        }
    }
}

@Preview
@Composable
fun ProductNotAvailableCardComponent(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    ),
                    color = Color.Black,
                    text = "Producto B"
                )
                Text(
                    text = "Sin Stock",
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Red, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            Text(
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                text = "$150.00",
                color = PrimaryColor,
            )

            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Normal
                ),
                text = "Cantidad solicitada: 2",
                color = RedTextComponent,
            )
        }
    }
}

@Preview
@Composable
fun WarningTextComponent(){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextGreyComponent,
        textAlign = TextAlign.Center,
        text = "Por favor, regrese a la sección de productos para ajustar su pedido."
    )
}