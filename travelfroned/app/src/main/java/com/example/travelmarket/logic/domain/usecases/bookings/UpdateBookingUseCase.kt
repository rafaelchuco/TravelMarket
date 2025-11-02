package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

class UpdateBookingUseCase(
    private val repository: BookingsRepository
) {
    suspend operator fun invoke(
        id: Int,
        request: UpdateBookingRequest
    ): NetworkResult<BookingDetail> {
        return repository.updateBooking(id, request)
    }
}
