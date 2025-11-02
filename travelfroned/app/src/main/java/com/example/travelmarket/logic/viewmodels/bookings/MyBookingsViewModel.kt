package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.usecases.bookings.GetMyBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyBookingsViewModel(
    private val getMyBookingsUseCase: GetMyBookingsUseCase
) : ViewModel() {

    private val _myBookingsState = MutableStateFlow<NetworkResult<List<Booking>>?>(null)
    val myBookingsState: StateFlow<NetworkResult<List<Booking>>?> = _myBookingsState.asStateFlow()

    fun getMyBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _myBookingsState.value = NetworkResult.Loading
            _myBookingsState.value = getMyBookingsUseCase(search, ordering, page)
        }
    }
}