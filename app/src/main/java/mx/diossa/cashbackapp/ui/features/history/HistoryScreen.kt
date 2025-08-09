package mx.diossa.cashbackapp.ui.features.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.HeaderTitleText
import mx.diossa.cashbackapp.ui.components.SearchBar
import mx.diossa.cashbackapp.ui.components.TicketsDone

@Composable
fun HistoryScreen(navController: NavHostController){
    var searchText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            HeaderTitleText()
            Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))
            SearchBar(
                query = searchText,
                onQueryChange = {searchText = it},
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth())
            TicketsDone()
        }
    }
}