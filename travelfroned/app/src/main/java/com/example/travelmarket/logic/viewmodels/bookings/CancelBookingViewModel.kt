package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.usecases.bookings.CancelBookingParams
import com.example.travelmarket.logic.domain.usecases.bookings.CancelBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CancelBookingViewModel(
    private val cancelBookingUseCase: CancelBookingUseCase
) : ViewModel() {

    private val _cancelBookingState = MutableStateFlow<NetworkResult<BookingDetail>>(NetworkResult.Loading)
    val cancelBookingState: StateFlow<NetworkResult<BookingDetail>> = _cancelBookingState.asStateFlow()

    fun cancelBooking(id: Int) {
        viewModelScope.launch {
            _cancelBookingState.value = NetworkResult.Loading
            _cancelBookingState.value = cancelBookingUseCase(
                CancelBookingParams(id = id)
            )
        }
    }
}