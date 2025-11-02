package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

class DeleteBookingUseCase(
    private val repository: BookingsRepository
) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deleteBooking(id)
    }
}