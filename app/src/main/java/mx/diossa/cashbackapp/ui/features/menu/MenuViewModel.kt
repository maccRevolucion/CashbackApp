package mx.diossa.cashbackapp.ui.features.menu

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(): ViewModel(){
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