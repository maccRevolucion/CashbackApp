package mx.diossa.cashbackapp.ui.features.menu

import android.content.SharedPreferences
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
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.repository.TicketRepository
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel(){
    private val _recentTickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val recentTickets: StateFlow<List<TicketEntity>> = _recentTickets.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _employeeName = MutableStateFlow("")
    val employeeName: StateFlow<String> = _employeeName.asStateFlow()

    init {
        _employeeName.value = sharedPreferences.getString("employee_name", "Empleado") ?: "Empleado"
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay()
                val tickets = ticketRepository.getRecentCompleteForToday(startOfToday)

                _recentTickets.value = tickets
                Log.d("MENU_VIEW_MODEL", "Fetched ${tickets.size} recent tickets for today")
            } catch (e: Exception) {
                _error.value = "Error fetching tickets: ${e.message}"
                Log.e("MENU_VIEW_MODEL", "Error: ${e.message}")
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
        object NavigateToLogin : NavigationEvent()
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

    fun onLogoutClicked() {
        sharedPreferences.edit()
            .remove("is_logged_in")
            .remove("access_token")
            .remove("employee_name")
            .apply()

        _navigationEvent.value = NavigationEvent.NavigateToLogin
    }
}