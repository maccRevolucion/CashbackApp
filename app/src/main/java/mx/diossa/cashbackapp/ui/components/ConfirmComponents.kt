package mx.diossa.cashbackapp.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.diossa.cashbackapp.domain.model.SelectedProduct
import mx.diossa.cashbackapp.ui.theme.BlueBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.BlueComponent
import mx.diossa.cashbackapp.ui.theme.Grey95
import mx.diossa.cashbackapp.ui.theme.GreyBackgroundComponent
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.TextColor
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent


@Composable
fun HeaderTitleComponent(onBack: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        IconButton(onClick = onBack ) {
            Icon(
                imageVector = Icons.Filled.KeyboardBackspace,
                contentDescription = ""
            )
        }
        Text(
            text = "Confirmar Canje",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            modifier = Modifier.align(Alignment.CenterVertically),
            color = TextColor,
        )
    }
}

@Composable
fun PrinterInfoCardComponent(isConnected: Boolean){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Detalles de la impresión")
            Spacer(modifier = Modifier.height(8.dp))
            ItemPrinterInfoComponent()
            if(isConnected){
                ItemPrinterStatusComponent()
            } else {
                ItemNoPrinterStatusComponent()
            }
        }
    }
}

@Composable
fun ButtonBottomComponent(onSelect: () -> Unit){
    Button(
        onClick = onSelect,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = "confirmar",
            tint = Color.White
        )
        Text(
            text = "Confirmar y Procesar",
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun ItemPrinterStatusComponent(){
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
                    text = "Impresora conectada y lista"
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemNoPrinterStatusComponent(){
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
                    text = "Impresora no conectada."
                )
            }
        }
    }
}

@Composable
fun ItemPrinterInfoComponent(){
    Surface(
        color = GreyBackgroundComponent,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            ){
            Text(
                color = Color.Black,
                text = "Se generará una nota de venta tipo Obsequio que será impresa automaticamente al confirmar."
            )
        }
    }
}

@Composable
fun ticketResume(
    selectedProducts: List<SelectedProduct>,
    total: Double,
    date: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Resumen del Canje")
            Spacer(modifier = Modifier.height(8.dp))
            ticketDetailsCardComponent(date = date)
            selectedProducts.forEach { product ->
                ItemProductSelectedComponent(
                    name = product.name,
                    price = product.price,
                    quantity = product.quantity
                )
            }
            ItemTotalComponent(total = total)
        }
    }
}

@Composable
fun ticketDetailsCardComponent(date: String){
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(4.dp),
        color = Grey95,
        tonalElevation = 2.dp,
    ) {
        Column {
            ItemTicketDetailsComponent(concept = "Saldo:", value = "T-12345")
            ItemTicketDetailsComponent(concept = "Vendedor:", value = "Juan Peréz")
            ItemTicketDetailsComponent(concept = "Fecha:", value = date)
        }
    }
}
@Composable
fun ItemProductSelectedComponent(
    name: String,
    price: Double,
    quantity: Int
){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 1.dp
        ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    ),
                    color = Color.Black,
                    text = name
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),
                    color = TextGreyComponent,
                    text = "$$price x $quantity"
                )
            }

            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                text = "$${price * quantity}",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun ItemTotalComponent(total: Double){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    ),
                    color = Color.Black,
                    text = "Total:"
                )
            }

            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = PrimaryColor,
                text = "$$total",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun ItemTicketDetailsComponent(concept: String, value: String){
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