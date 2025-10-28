package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.usecases.bookings.DeleteBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteBookingViewModel(
    private val deleteBookingUseCase: DeleteBookingUseCase
) : ViewModel() {

    private val _deleteBookingState = MutableStateFlow<NetworkResult<Unit>?>(null)
    val deleteBookingState: StateFlow<NetworkResult<Unit>?> = _deleteBookingState.asStateFlow()

    fun deleteBooking(id: Int) {
        viewModelScope.launch {
            _deleteBookingState.value = NetworkResult.Loading
            _deleteBookingState.value = deleteBookingUseCase(id)
        }
    }
}