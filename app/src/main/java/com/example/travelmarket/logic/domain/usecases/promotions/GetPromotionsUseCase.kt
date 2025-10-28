package com.example.travelmarket.logic.domain.usecases.promotions

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.domain.repositories.PromotionsRepository

class GetPromotionsUseCase(
    private val repository: PromotionsRepository
) : BaseUseCaseWithParams<GetPromotionsUseCase.Params, NetworkResult<PaginatedResponse<Promotion>>>() {

    override suspend fun invoke(params: Params): NetworkResult<PaginatedResponse<Promotion>> {
        return repository.getPromotions(
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }

    data class Params(
        val search: String? = null,
        val ordering: String? = null,
        val page: Int? = null
    )
}