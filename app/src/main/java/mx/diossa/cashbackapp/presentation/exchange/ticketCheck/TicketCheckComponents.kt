package mx.diossa.cashbackapp.presentation.exchange.ticketCheck

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.diossa.cashbackapp.presentation.ui.theme.GreenBackground
import mx.diossa.cashbackapp.presentation.ui.theme.GreenCompleted
import mx.diossa.cashbackapp.presentation.ui.theme.Grey200
import mx.diossa.cashbackapp.presentation.ui.theme.Red40
import mx.diossa.cashbackapp.presentation.ui.theme.TextColor

@Composable
fun HeaderComponent(onBack: () -> Unit){
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
            text = "Detalles del Ticket",
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
fun ticketDetails(onSelect: () -> Unit){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(300.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
        ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = TextColor,
                text = "Ticket # 1234"
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Válido",
                color = Color.White,
                modifier = Modifier
                    .background(GreenCompleted, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = Grey200,
                text = "Fecha:"
            )

            Text(
                text = "26/07/2025"
            )
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = Grey200,
                text = "Ticket:"
            )
            Text(text = "Juan Perez")
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = Grey200,
                text = "Saldo disponible:"
            )
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    fontSize = 17.sp
                ),
                color = Red40,
                text = "$500.00"
            )
        }
        bottomItem()
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
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
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Carro de compras",
                    tint = Color.White
                )
                Text(
                    text = "Seleccionar Productos",
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun BottonSelectProducts(onSelect: () -> Unit){

}

@Preview
@Composable
fun bottomItem(){
     Surface(
         color = GreenBackground,
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
                 color = GreenCompleted,
                 textAlign = TextAlign.Center,
                 text = "Este ticket es válido y puede ser canjeado por productos"
             )
         }
     }
}

@Composable
fun ImportantInfoCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título en negrita
            Text(
                text = "Información Importante",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Puntos clave
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = "•", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "El saldo debe ser canjeado en su totalidad en una sola transacción.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    color = Grey200
                )
            }
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = "•", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Solo puede seleccionar productos elegibles para este tipo de canje.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    color = Grey200
                )
            }
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = "•", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Una vez confirmado el canje, no se podrá modificar.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    color = Grey200
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview_ImportanInfo(){
    ImportantInfoCard()
}