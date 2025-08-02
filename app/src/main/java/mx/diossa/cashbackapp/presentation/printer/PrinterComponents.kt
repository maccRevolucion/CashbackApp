package mx.diossa.cashbackapp.presentation.printer

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import mx.diossa.cashbackapp.presentation.ui.theme.BlueGrey80
import mx.diossa.cashbackapp.presentation.ui.theme.BlueGrey90
import mx.diossa.cashbackapp.presentation.ui.theme.GreenBackground
import mx.diossa.cashbackapp.presentation.ui.theme.GreenCompleted
import mx.diossa.cashbackapp.presentation.ui.theme.Grey20
import mx.diossa.cashbackapp.presentation.ui.theme.Grey200
import mx.diossa.cashbackapp.presentation.ui.theme.Grey80
import mx.diossa.cashbackapp.presentation.ui.theme.Grey90
import mx.diossa.cashbackapp.presentation.ui.theme.Grey95
import mx.diossa.cashbackapp.presentation.ui.theme.Red20
import mx.diossa.cashbackapp.presentation.ui.theme.Red40
import mx.diossa.cashbackapp.presentation.ui.theme.Red90
import mx.diossa.cashbackapp.presentation.ui.theme.TextColor

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

@Composable
fun printerStatus(){
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

            Text(
                text = "Conectada",
                color = GreenCompleted,
                modifier = Modifier
                    .background(GreenBackground, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Surface(
            modifier = Modifier
                .padding(12.dp)
                .width(380.dp)
                .height(180.dp)
                .align(Alignment.CenterHorizontally),
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
                        color = Red90,
                        tonalElevation = 2.dp,
                    ) {
                        Icon(
                            modifier = Modifier.padding(5.dp),
                            imageVector = Icons.Outlined.Print,
                            contentDescription = "",
                            tint = Red40
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.Top)
                    ) {
                        Text(
                            text = "Star TS100",
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

                    // TODO Actualizarlo por un Spacer u otro componente
                    // Componente que no se usa pero mantiene el orden de los demás componentes
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "")
                        Text(text = "")
                    }


                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            color = Grey200,
                            text = "Estado:"
                        )
                        Text(
                            color = GreenCompleted,
                            text = "Lista para imprimir"
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
                            color = Grey200,
                            text = "Tipo de conexión:"
                        )
                        Text(text = "Bluetooth")
                    }

                }

            }
        }

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
                    tint = Grey200
                )
                Text(
                    text = "Actualizar",
                    color = Grey200,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterConfig() {
    var selectedPaper by remember { mutableStateOf("") }
    val paperOptions = listOf("80mm", "120mm", "950mm")

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

            Column(modifier = Modifier
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
                    value = "",
                    onValueChange = { /* TODO: actualizar nombre */ },
                    placeholder = { Text("Ej. MiImpresoraBT") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Tamaño del papel
                Text(text = "Tamaño del papel")
                ExposedDropdownMenuBox(
                    expanded = selectedPaper.isNotEmpty(),
                    onExpandedChange = { /* se abre al hacer click */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    OutlinedTextField(
                        value = selectedPaper,
                        onValueChange = { /* readOnly */ },
                        readOnly = true,
                        placeholder = { Text("Seleccionar...") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = selectedPaper.isNotEmpty()
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { /* TODO: cerrar menu */ }
                    ) {
                        paperOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedPaper = option
                                    /* TODO: cerrar menu */
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                //Boton de Guardado
                Button(
                    onClick = { /*TODO*/ },
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

@Preview
@Composable
fun Preview_PrinterConfig(){
    PrinterConfig()
}