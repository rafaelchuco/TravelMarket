package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

data class GetReviewsParams(
    val search: String? = null,
    val ordering: String? = null,
    val page: Int? = null
)

class GetReviewsUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<GetReviewsParams, NetworkResult<List<Review>>>() {

    override suspend fun invoke(params: GetReviewsParams): NetworkResult<List<Review>> {
        return repository.getReviews(
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }
}