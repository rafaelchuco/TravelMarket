package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

class GetMyBookingsUseCase(
    private val repository: BookingsRepository
) {
    suspend operator fun invoke(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Booking>> {
        return repository.getMyBookings(search, ordering, page)
    }
}