package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

data class GetMyReviewsParams(
    val search: String? = null,
    val ordering: String? = null,
    val page: Int? = null
)

class GetMyReviewsUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<GetMyReviewsParams, NetworkResult<List<Review>>>() {

    override suspend fun invoke(params: GetMyReviewsParams): NetworkResult<List<Review>> {
        return repository.getMyReviews(
            search = params.search,
            ordering = params.ordering,
            page = params.page
        )
    }
}