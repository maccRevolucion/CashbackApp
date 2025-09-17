package mx.diossa.cashbackapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.diossa.cashbackapp.R
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.ui.features.menu.MenuViewModel
import mx.diossa.cashbackapp.ui.theme.Grey20
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.SecondaryColor
import mx.diossa.cashbackapp.ui.theme.TextColor

// En: MenuComponents.kt

@Composable
fun TextUserHeader(
    employeeName: String,
    onProfileClick: () -> Unit,
    showLogoutMenu: Boolean,
    onDismissMenu: () -> Unit,
    onLogoutClick: () -> Unit,
    onClosingDay: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Bienvenido",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextColor
                )
                Text(
                    text = employeeName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = TextColor
                )
            }

            Image(
                painter = painterResource(id = R.drawable.dog),
                contentDescription = "Perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
            )
        }
        DropdownMenu(
            expanded = showLogoutMenu,
            onDismissRequest = { onDismissMenu() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            DropdownMenuItem(
                text = { Text("Cerrar sesión") },
                onClick = { onLogoutClick() }
            )
            DropdownMenuItem(
                text = { Text("Cierre del día") },
                onClick = { onClosingDay() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_Header(){
    TextUserHeader(employeeName = "Cesar Covantes", onProfileClick = {}, onDismissMenu = {}, onLogoutClick = {}, onClosingDay = {}, showLogoutMenu = false)
}
@Preview
@Composable
fun Preview_ItemTicketsPerday(){
    ItemTicketsPerDay(tickets = "128")
}


@Composable
@Preview
fun Preview_ItemButtons(){
    Row (){
        ItemButtons(action = "Escanear QR", subtitle = "Procesar nuevo ticket", icon = Icons.Filled.QrCode2){}
        ItemButtons(action = "Historial", subtitle = "Ver tickets procesados", icon = Icons.Filled.History) {}
    }
}

@Composable
fun ItemTicketsPerDay(tickets: String){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(65.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tickets procesados hoy",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.TopStart)
            )
            Text(
                text = tickets,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomStart)
            )

            Surface(modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .align(Alignment.CenterEnd),
                shape = CircleShape,
                color = Color.White,
                tonalElevation = 2.dp,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShowChart,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(5.dp)
                        .size(25.dp),
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun ItemButtons(action: String, subtitle: String, icon: ImageVector,onClick: () -> Unit){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .width(150.dp)
            .height(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                color = SecondaryColor,
                tonalElevation = 2.dp,
            ){
                Icon(
                    modifier = Modifier.padding(6.dp),
                    imageVector = icon,
                    contentDescription = "",
                    tint = PrimaryColor
                )
            }
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = action,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = subtitle,
                color = Grey20,
                fontSize = 9.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
fun recentActivity(tickets: List<TicketEntity>, viewModel: MenuViewModel) {
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
        Row(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Actividad reciente",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            if(tickets.isEmpty()){
                Text(
                    text = "No hay actividad reciente",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                tickets.forEach { ticket ->
                    ItemRecentComponent(
                        ticketId = ticket.ticketNumber,
                        timePassed = viewModel.calculateTimePassed(ticket.date),
                        cashback = ticket.amount.toString(),
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ItemRecentComponent(ticketId: String, timePassed: String, cashback: String, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Surface(
                    modifier = Modifier
                        .size(40.dp),
                    shape = CircleShape,
                    color = SecondaryColor,
                    tonalElevation = 2.dp,
                ){
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Filled.QrCode2,
                        tint = PrimaryColor,
                        contentDescription = "")
                }
                Column {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        text = "Ticket # $ticketId"
                    )
                    Text(
                        color = Grey20,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Normal,
                        text = "Procesado hace $timePassed"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                text = "$${cashback}.00"
            )
        }
    }
}