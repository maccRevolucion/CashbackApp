package mx.diossa.cashbackapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun QrScannerOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    )
}


@Composable
fun QrInstructions(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        modifier = modifier
    )
}