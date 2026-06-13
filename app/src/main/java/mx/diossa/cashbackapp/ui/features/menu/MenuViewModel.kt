package mx.diossa.cashbackapp.ui.features.menu

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.core.utils.PrinterConnection
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.repository.TicketRepository
import mx.diossa.cashbackapp.domain.ticket.TicketGenerator
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val sharedPreferences: SharedPreferences,
    private val ticketGenerator: TicketGenerator,
    private val printerConnection: PrinterConnection,
    @param:ApplicationContext private val context: Context
): ViewModel(){
    private val _recentTickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val recentTickets: StateFlow<List<TicketEntity>> = _recentTickets.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _employeeName = MutableStateFlow("")
    val employeeName: StateFlow<String> = _employeeName.asStateFlow()

    private val _ticketsCount = MutableStateFlow(0)
    val ticketsCount: StateFlow<Int> = _ticketsCount.asStateFlow()

    init {
        _employeeName.value = sharedPreferences.getString("employee_name", "Empleado") ?: "Empleado"
        viewModelScope.launch {
            val startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay()
            ticketRepository.getRecentCompleteForToday(startOfToday).collect { tickets ->
                _recentTickets.value = tickets
                _isLoading.value = false
            }
        }

        viewModelScope.launch {
            val startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay()
            ticketRepository.getAllCompleted(startOfToday).collect { tickets ->
                _ticketsCount.value = tickets.size
                _isLoading.value = false
            }
        }
    }

    fun refreshRecentTickets() {
        viewModelScope.launch {
            val startOfToday = java.time.LocalDate.now().atStartOfDay()
            ticketRepository.getRecentCompleteForToday(startOfToday).collect { tickets ->
                _recentTickets.value = tickets
                _ticketsCount.value = tickets.size
            }
        }

        viewModelScope.launch {
            val startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay()
            ticketRepository.getAllCompleted(startOfToday).collect { tickets ->
                _ticketsCount.value = tickets.size
            }
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
    fun onClosingDay() {
        viewModelScope.launch {
            try {
                ticketRepository.getTicketsWithProductsForDate(java.time.LocalDate.now())
                    .collect { tickets ->
                        if (tickets.isEmpty()) {
                            _error.value = "No hay tickets para hoy"
                            return@collect
                        }

                        val report = ticketGenerator.buildDailySummaryTicket(
                            tickets,
                            employeeName.value
                        )

                        if (printerConnection.isConnected()) {
                            printerConnection.sendMessage(report)

                            // 1. Guardar fecha de cierre en SharedPreferences
                            sharedPreferences.edit()
                                .putString("last_closing_date", java.time.LocalDate.now().toString())
                                .apply()

                            // 2. Limpiar base de datos local
                            ticketRepository.clearAll()

                            // 3. Bloquear sesión
                            sharedPreferences.edit()
                                .putBoolean("is_logged_in", false)
                                .apply()

                            _navigationEvent.value = NavigationEvent.NavigateToLogin
                        } else {
                            _error.value = "Impresora no conectada"
                        }
                    }
            } catch (e: Exception) {
                _error.value = "Error al generar cierre: ${e.message}"
            }
        }
    }


    fun clearError(){
        _error.value = null
    }
}