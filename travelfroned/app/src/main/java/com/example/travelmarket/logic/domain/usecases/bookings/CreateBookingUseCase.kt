package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

class CreateBookingUseCase(
    private val repository: BookingsRepository
) {
    suspend operator fun invoke(request: CreateBookingRequest): NetworkResult<Booking> {
        return repository.createBooking(request)
    }
}
