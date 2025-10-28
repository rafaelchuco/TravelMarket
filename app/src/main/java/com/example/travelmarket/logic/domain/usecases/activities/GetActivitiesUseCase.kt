package com.example.travelmarket.logic.domain.usecases.activities

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository

class GetActivitiesUseCase(
    private val repository: ActivitiesRepository
) : BaseUseCaseWithParams<GetActivitiesUseCase.Params, NetworkResult<PaginatedResponse<Activity>>>() {

    override suspend fun invoke(params: Params): NetworkResult<PaginatedResponse<Activity>> {
        return repository.getActivities(
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