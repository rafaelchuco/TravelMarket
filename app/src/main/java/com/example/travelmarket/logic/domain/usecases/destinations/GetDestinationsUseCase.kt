package com.example.travelmarket.logic.domain.usecases.destinations

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository

data class GetDestinationsParams(
    val country: String? = null,
    val continent: String? = null,
    val isPopular: Boolean? = null,
    val bestSeason: String? = null,
    val search: String? = null,
    val ordering: String? = null,
    val page: Int? = null
)

class GetDestinationsUseCase(
    private val repository: DestinationsRepository
) : BaseUseCaseWithParams<GetDestinationsParams, NetworkResult<List<Destination>>>() {

    override suspend fun invoke(params: GetDestinationsParams): NetworkResult<List<Destination>> {
        return repository.getDestinations(
            country = params.country,
            continent = params.continent,
            isPopular = params.isPopular,
            bestSeason = params.bestSeason,
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }
}