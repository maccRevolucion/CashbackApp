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

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): ViewModel() {
    private val _allCompletedTickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    private val _uiState = MutableStateFlow(UiStateTicket())
    val uiState: StateFlow<UiStateTicket> = _uiState.asStateFlow()

    val completedTickets: StateFlow<List<TicketEntity>> = _uiState.map { it.filteredTickets }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            _allCompletedTickets.value = ticketRepository.getAllCompleted()
            _uiState.value = UiStateTicket(filteredTickets = getFilteredTickets("", false, _allCompletedTickets.value))
        }
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                filteredTickets = getFilteredTickets(newQuery, current.isAscending, _allCompletedTickets.value)
            )
        }
    }

    fun onToggleOrder() {
        _uiState.update { current ->
            val newAscending = !current.isAscending
            current.copy(
                isAscending = newAscending,
                filteredTickets = getFilteredTickets(current.query, newAscending, _allCompletedTickets.value)
            )
        }
    }

    private fun getFilteredTickets(query: String, isAscending: Boolean, tickets: List<TicketEntity>): List<TicketEntity> {
        val filtered = tickets.filter { ticket ->
            ticket.id.contains(query, ignoreCase = true) ||
                    ticket.ticketNumber.contains(query, ignoreCase = true) ||
                    ticket.sellerName.contains(query, ignoreCase = true)
        }
        return if (isAscending) {
            filtered.sortedBy { it.date }
        } else {
            filtered.sortedByDescending { it.date }
        }
    }
}