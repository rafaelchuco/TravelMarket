package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

class DeleteReviewUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<Int, NetworkResult<Unit>>() {

    override suspend fun invoke(params: Int): NetworkResult<Unit> {
        return repository.deleteReview(params)
    }
}