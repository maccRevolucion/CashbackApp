package mx.diossa.cashbackapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import mx.diossa.cashbackapp.ui.theme.GreenCompletedComponent
import mx.diossa.cashbackapp.ui.theme.PrimaryColor
import mx.diossa.cashbackapp.ui.theme.TextColor
import mx.diossa.cashbackapp.ui.theme.TextGreyComponent

@Preview(showBackground = true)
@Composable
fun HeaderTitleText(){
    Row(modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Historial de Tickets",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHistory(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Buscar por ID, ticket o vendedor..."
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        placeholder = { Text(
            placeholderText,
            textAlign = TextAlign.Center
        ) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}


@Preview
@Composable
fun TicketsDone(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(650.dp),
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
            Text(
                text = "Tickets Procesados",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = "Filtrar",
                    tint = Color.Gray
                )
            }

        }
        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 4.dp)
        ) {
            ItemTicketDone("R-123456", "T-1001","Juan Perez", "30/07/2025 - 10:45 AM", 350)
            ItemTicketDone("R-123457", "T-1002","Maria Gonzales", "30/07/2025 - 09:50 AM", 500)
            ItemTicketDone("R-123458", "T-1003","Carlos Rodriguez", "30/07/2025 - 11:25 AM", 275)
            ItemTicketDone("R-123459", "T-1004","Manuel Cordero", "30/07/2025 - 13:33 PM", 433)
        }
    }
}

@Composable
fun ItemTicketDone(idFolio: String, idTicket: String, sellerName: String, date: String, cash: Int){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 3.dp
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
        ){
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = TextColor,
                  text = idFolio
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.TaskAlt,
                contentDescription = "",
                tint = GreenCompletedComponent,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Completado",
                color = GreenCompletedComponent
            )
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = TextGreyComponent,
                text = "Ticket:"
            )
            Text(text = idTicket)
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = TextGreyComponent,
                text = "Vendedor:"
            )
            Text(text = sellerName)
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                color = TextGreyComponent,
                text = "Fecha:"
            )
            Text(text = date)
        }

        HorizontalDivider()
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                text = "Monto:"
            )
            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = PrimaryColor,
                text = "$${cash}.00"
            )
        }
    }
}