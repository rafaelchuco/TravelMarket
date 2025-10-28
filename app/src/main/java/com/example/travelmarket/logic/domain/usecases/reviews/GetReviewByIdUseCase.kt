package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

class GetReviewByIdUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<Int, NetworkResult<Review>>() {

    override suspend fun invoke(params: Int): NetworkResult<Review> {
        return repository.getReviewById(params)
    }
}