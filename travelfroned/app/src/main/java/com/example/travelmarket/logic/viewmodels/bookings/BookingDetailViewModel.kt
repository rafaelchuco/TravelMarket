package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingByIdParams
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingDetailViewModel @Inject constructor(
    private val getBookingByIdUseCase: GetBookingByIdUseCase
) : ViewModel() {

    private val _bookingDetailState = MutableStateFlow<NetworkResult<BookingDetail>>(NetworkResult.Loading)
    val bookingDetailState: StateFlow<NetworkResult<BookingDetail>> = _bookingDetailState.asStateFlow()

    fun getBookingById(id: Int) {
        viewModelScope.launch {
            _bookingDetailState.value = NetworkResult.Loading
            _bookingDetailState.value = getBookingByIdUseCase(
                GetBookingByIdParams(id = id)
            )
        }
    }
}