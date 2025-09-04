package mx.diossa.cashbackapp.ui.features.exchange.ticketCheck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.diossa.cashbackapp.data.remote.dto.CashbackDetail
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

data class TicketCheckUiState(
    val details: CashbackDetail? = null,
    val isValid: Boolean = false
)

@HiltViewModel
class TicketCheckViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(TicketCheckUiState())
    val uiState = _uiState.asStateFlow()

    fun init(exchangeViewModel: ExchangeViewModel) {
        val encodedJson = savedStateHandle.get<String>("cashbackJson")
        val isValid = savedStateHandle.get<Boolean>("isValid") ?: false

        if (encodedJson != null) {
            val cashbackJson = URLDecoder.decode((encodedJson), StandardCharsets.UTF_8.toString())
            val details = Gson().fromJson(cashbackJson, CashbackDetail::class.java)
            _uiState.value = TicketCheckUiState(details = details, isValid = isValid)
            exchangeViewModel.setTicketDetails(details)
        }
    }
}
