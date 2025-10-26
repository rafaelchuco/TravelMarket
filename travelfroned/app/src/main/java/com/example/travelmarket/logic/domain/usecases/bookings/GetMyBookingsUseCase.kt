package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class GetMyBookingsParams(
    val search: String? = null,
    val ordering: String? = null,
    val page: Int? = null
)

class GetMyBookingsUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<GetMyBookingsParams, NetworkResult<List<BookingDetail>>>() {

    override suspend fun invoke(params: GetMyBookingsParams): NetworkResult<List<BookingDetail>> {
        return repository.getMyBookings(
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }
}