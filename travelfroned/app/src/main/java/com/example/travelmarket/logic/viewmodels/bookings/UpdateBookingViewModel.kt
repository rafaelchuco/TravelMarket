package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.usecases.bookings.UpdateBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateBookingViewModel(
    private val updateBookingUseCase: UpdateBookingUseCase
) : ViewModel() {

    private val _updateBookingState = MutableStateFlow<NetworkResult<BookingDetail>>(NetworkResult.Loading)
    val updateBookingState: StateFlow<NetworkResult<BookingDetail>> = _updateBookingState.asStateFlow()

    fun updateBooking(
        id: Int,
        bookingNumber: String?,
        travelDate: String?,
        returnDate: String?,
        numAdults: Int?,
        numChildren: Int?,
        numInfants: Int?,
        subtotal: String?,
        discountAmount: String?,
        taxAmount: String?,
        totalAmount: String?,
        paidAmount: String?,
        status: String?,
        paymentStatus: String?,
        specialRequests: String?,
        customer: Int?,
        packageId: Int?
    ) {
        viewModelScope.launch {
            _updateBookingState.value = NetworkResult.Loading

            val request = UpdateBookingRequest(
                bookingNumber = bookingNumber,
                travelDate = travelDate,
                returnDate = returnDate,
                numAdults = numAdults,
                numChildren = numChildren,
                numInfants = numInfants,
                subtotal = subtotal,
                discountAmount = discountAmount,
                taxAmount = taxAmount,
                totalAmount = totalAmount,
                paidAmount = paidAmount,
                status = status,
                paymentStatus = paymentStatus,
                specialRequests = specialRequests,
                customer = customer,
                packageId = packageId
            )

            _updateBookingState.value = updateBookingUseCase(id, request)
        }
    }
}
