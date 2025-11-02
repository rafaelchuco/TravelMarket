package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class GetBookingsParams(
    val search: String? = null,
    val ordering: String? = null,
    val page: Int? = null
)

class GetBookingsUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<GetBookingsParams, NetworkResult<List<Booking>>>() {

    override suspend fun invoke(params: GetBookingsParams): NetworkResult<List<Booking>> {
        return repository.getBookings(
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }
}