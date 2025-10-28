package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.usecases.bookings.CreateBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateBookingViewModel(
    private val createBookingUseCase: CreateBookingUseCase
) : ViewModel() {

    private val _createBookingState = MutableStateFlow<NetworkResult<Booking>?>(null)
    val createBookingState: StateFlow<NetworkResult<Booking>?> = _createBookingState.asStateFlow()

    fun createBooking(
        packageId: Int,
        travelDate: String,
        returnDate: String?,
        numAdults: Int,
        numChildren: Int,
        numInfants: Int,
        totalAmount: Double,  // ✅ AGREGADO
        specialRequests: String?
    ) {
        viewModelScope.launch {
            _createBookingState.value = NetworkResult.Loading

            val request = CreateBookingRequest(
                packageId = packageId,
                travelDate = travelDate,
                returnDate = returnDate,
                numAdults = numAdults,
                numChildren = numChildren,
                numInfants = numInfants,
                totalAmount = totalAmount,  // ✅ AGREGADO
                specialRequests = specialRequests
            )

            _createBookingState.value = createBookingUseCase(request)
        }
    }

    fun resetCreateState() {
        _createBookingState.value = null
    }
}
