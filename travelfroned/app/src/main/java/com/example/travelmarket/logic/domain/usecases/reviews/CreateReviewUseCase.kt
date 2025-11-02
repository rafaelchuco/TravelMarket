package com.example.travelmarket.logic.domain.usecases.reviews

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

data class CreateReviewParams(
    val overallRating: Int,
    val accommodationRating: Int?,
    val transportRating: Int?,
    val guideRating: Int?,
    val valueRating: Int?,
    val title: String,
    val comment: String,
    val pros: String?,
    val cons: String?,
    val bookingId: Int,
    val packageId: Int
)

class CreateReviewUseCase(
    private val repository: ReviewsRepository
) : BaseUseCaseWithParams<CreateReviewParams, NetworkResult<Review>>() {

    override suspend fun invoke(params: CreateReviewParams): NetworkResult<Review> {
        return repository.createReview(
            overallRating = params.overallRating,
            accommodationRating = params.accommodationRating,
            transportRating = params.transportRating,
            guideRating = params.guideRating,
            valueRating = params.valueRating,
            title = params.title,
            comment = params.comment,
            pros = params.pros,
            cons = params.cons,
            bookingId = params.bookingId,
            packageId = params.packageId
        )
    }
}