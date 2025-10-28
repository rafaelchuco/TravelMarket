package com.example.travelmarket.logic.domain.usecases.promotions

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.domain.repositories.PromotionsRepository

class GetPromotionDetailUseCase(
    private val repository: PromotionsRepository
) : BaseUseCaseWithParams<Int, NetworkResult<Promotion>>() {

    override suspend fun invoke(params: Int): NetworkResult<Promotion> {
        return repository.getPromotionById(params)
    }
}