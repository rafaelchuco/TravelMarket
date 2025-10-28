package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingsParams
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingsListViewModel(
    private val getBookingsUseCase: GetBookingsUseCase
) : ViewModel() {

    private val _bookingsState = MutableStateFlow<NetworkResult<List<Booking>>>(NetworkResult.Loading)
    val bookingsState: StateFlow<NetworkResult<List<Booking>>> = _bookingsState.asStateFlow()

    init {
        getBookings()
    }

    fun getBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _bookingsState.value = NetworkResult.Loading
            _bookingsState.value = getBookingsUseCase(
                GetBookingsParams(
                    search = search,
                    ordering = ordering,
                    page = page
                )
            )
        }
    }
}