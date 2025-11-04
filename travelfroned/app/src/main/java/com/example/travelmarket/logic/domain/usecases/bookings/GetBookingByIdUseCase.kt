package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
import javax.inject.Inject

data class GetBookingByIdParams(
    val id: Int
)

class GetBookingByIdUseCase @Inject constructor(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<GetBookingByIdParams, NetworkResult<BookingDetail>>() {

    override suspend fun invoke(params: GetBookingByIdParams): NetworkResult<BookingDetail> {
        return repository.getBookingById(params.id)
    }
}