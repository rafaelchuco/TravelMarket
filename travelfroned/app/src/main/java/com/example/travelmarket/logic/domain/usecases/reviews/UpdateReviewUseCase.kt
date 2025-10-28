package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

data class UpdateReviewParams(
    val id: Int,
    val overallRating: Int?,
    val accommodationRating: Int?,
    val transportRating: Int?,
    val guideRating: Int?,
    val valueRating: Int?,
    val title: String?,
    val comment: String?,
    val pros: String?,
    val cons: String?
)

class UpdateReviewUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<UpdateReviewParams, NetworkResult<Review>>() {

    override suspend fun invoke(params: UpdateReviewParams): NetworkResult<Review> {
        return repository.updateReview(
            id = params.id,
            overallRating = params.overallRating,
            accommodationRating = params.accommodationRating,
            transportRating = params.transportRating,
            guideRating = params.guideRating,
            valueRating = params.valueRating,
            title = params.title,
            comment = params.comment,
            pros = params.pros,
            cons = params.cons
        )
    }
}