package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class DeleteBookingParams(
    val id: Int
)

class DeleteBookingUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<DeleteBookingParams, NetworkResult<Unit>>() {

    override suspend fun invoke(params: DeleteBookingParams): NetworkResult<Unit> {
        return repository.deleteBooking(params.id)
    }
}