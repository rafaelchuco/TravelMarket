package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class CancelBookingParams(
    val id: Int
)

class CancelBookingUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<CancelBookingParams, NetworkResult<BookingDetail>>() {

    override suspend fun invoke(params: CancelBookingParams): NetworkResult<BookingDetail> {
        return repository.cancelBooking(params.id)
    }
}