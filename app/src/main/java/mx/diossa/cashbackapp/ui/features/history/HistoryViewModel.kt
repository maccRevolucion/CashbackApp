package mx.diossa.cashbackapp.ui.features.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.diossa.cashbackapp.data.local.entity.TicketEntity
import mx.diossa.cashbackapp.data.repository.TicketRepository
import mx.diossa.cashbackapp.domain.model.UiStateTicket  // Asume data class UiStateTicket(val query: String = "", val isAscending: Boolean = false, val filteredTickets: List<TicketEntity> = emptyList())
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiStateTicket())
    val uiState: StateFlow<UiStateTicket> = _uiState.asStateFlow()

    private val _allTickets = MutableStateFlow<List<TicketEntity>>(emptyList())

    val completedTickets: StateFlow<List<TicketEntity>> =
        _uiState.map { it.filteredTickets }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            val startOfDay = java.time.LocalDate.now().atStartOfDay()
            ticketRepository.getAllCompleted(startOfDay).collect { tickets ->
                _allTickets.value = tickets
                _uiState.update {
                    it.copy(filteredTickets = getFilteredTickets("", it.isAscending, tickets))
                }
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                filteredTickets = getFilteredTickets(newQuery, current.isAscending, _allTickets.value)
            )
        }
    }

    fun onToggleOrder() {
        _uiState.update { current ->
            val newAscending = !current.isAscending
            current.copy(
                isAscending = newAscending,
                filteredTickets = getFilteredTickets(current.query, newAscending, _allTickets.value)
            )
        }
    }

    private fun getFilteredTickets(
        query: String,
        isAscending: Boolean,
        tickets: List<TicketEntity>
    ): List<TicketEntity> {
        val filtered = tickets.filter { ticket ->
            ticket.id.contains(query, ignoreCase = true) ||
                    ticket.ticketNumber.contains(query, ignoreCase = true) ||
                    ticket.employeeName.contains(query, ignoreCase = true)
        }
        return if (isAscending) filtered.sortedBy { it.date }
        else filtered.sortedByDescending { it.date }
    }
}