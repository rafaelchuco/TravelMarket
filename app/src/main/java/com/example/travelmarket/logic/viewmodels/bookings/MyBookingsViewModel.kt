package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.usecases.bookings.GetMyBookingsParams
import com.example.travelmarket.logic.domain.usecases.bookings.GetMyBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyBookingsViewModel(
    private val getMyBookingsUseCase: GetMyBookingsUseCase
) : ViewModel() {

    private val _myBookingsState = MutableStateFlow<NetworkResult<List<BookingDetail>>>(NetworkResult.Loading)
    val myBookingsState: StateFlow<NetworkResult<List<BookingDetail>>> = _myBookingsState.asStateFlow()

    init {
        getMyBookings()
    }

    fun getMyBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _myBookingsState.value = NetworkResult.Loading
            _myBookingsState.value = getMyBookingsUseCase(
                GetMyBookingsParams(
                    search = search,
                    ordering = ordering,
                    page = page
                )
            )
        }
    }
}