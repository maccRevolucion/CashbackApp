package mx.diossa.cashbackapp.ui.features.exchange.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.diossa.cashbackapp.ui.components.HeaderTitleProductComponent
import mx.diossa.cashbackapp.ui.components.ItemProduct
import mx.diossa.cashbackapp.ui.components.SearchBar
import mx.diossa.cashbackapp.ui.components.SubHeader
import mx.diossa.cashbackapp.ui.components.bottomButton
import mx.diossa.cashbackapp.ui.components.inforCard

@Composable
fun ProductScreen(navController: NavHostController){
    var searchText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderTitleProductComponent(onBack = { navController.popBackStack() })
                Spacer(modifier = Modifier.height(18.dp))
                inforCard()
                Spacer(modifier = Modifier.height(10.dp))
                SubHeader()
                Spacer(modifier = Modifier.height(8.dp))
                SearchBar(
                    query = searchText,
                    onQueryChange = { searchText = it },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                ItemProduct()
                ItemProduct()
                ItemProduct()
            }
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)){
                bottomButton(onSelected = {navController.navigate("confirm")})
            }
        }
    }

}