package mx.diossa.cashbackapp.ui.features.menu

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.data.entity.TicketEntity
import mx.diossa.cashbackapp.data.repository.TicketRepository
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): ViewModel(){
    private val _recentTickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val recentTickets: StateFlow<List<TicketEntity>> = _recentTickets.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val tickets = ticketRepository.getRecentComplete()
                _recentTickets.value = tickets
                Log.d("MenuVM", "Fetched ${tickets.size} recent tickets")
            } catch (e: Exception) {
                _error.value = "Error fetching tickets: ${e.message}"
                Log.e("MenuVM", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun refreshRecentTickets() {
        viewModelScope.launch {
            _recentTickets.value = ticketRepository.getRecentComplete()
        }
    }

    fun calculateTimePassed(date: LocalDateTime): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(date, now)
        return when {
            duration.toDays() > 0 -> "${duration.toDays()} días"
            duration.toHours() > 0 -> "${duration.toHours()} horas"
            else -> "${duration.toMinutes()} minutos"
        }
    }

    sealed class NavigationEvent {
        object NavigateToScanQR : NavigationEvent()
        object NavigateToHistory : NavigationEvent()
    }

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    fun onScanQRClicked() {
        _navigationEvent.value = NavigationEvent.NavigateToScanQR
    }
    fun onHistoryClicked() {
        _navigationEvent.value = NavigationEvent.NavigateToHistory
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}