package com.example.travelmarket.logic.domain.usecases.activities

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository

class GetActivityDetailUseCase(
    private val repository: ActivitiesRepository
) : BaseUseCaseWithParams<Int, NetworkResult<Activity>>() {

    override suspend fun invoke(params: Int): NetworkResult<Activity> {
        return repository.getActivityById(params)
    }
}