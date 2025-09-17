package mx.diossa.cashbackapp.ui.features.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.HeaderTitleText
import mx.diossa.cashbackapp.ui.components.SearchBarHistory
import mx.diossa.cashbackapp.ui.components.TicketsDoneComponent

@Composable
fun HistoryScreen(navController: NavHostController, viewModel: HistoryViewModel = hiltViewModel()){
    val uiState = viewModel.uiState.collectAsState().value
    val completedTickets by viewModel.completedTickets.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            HeaderTitleText()
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            SearchBarHistory(
                query = uiState.query,
                onQueryChange = viewModel::onQueryChanged,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth())
            TicketsDoneComponent(
                tickets = completedTickets,
                onFilterClick = viewModel::onToggleOrder
            )
        }
    }
}