package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.ReviewMapper
import com.example.travelmarket.logic.data.models.request.reviews.CreateReviewRequest
import com.example.travelmarket.logic.data.models.request.reviews.UpdateReviewRequest
import com.example.travelmarket.logic.data.remote.reviews.ReviewsApiService
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository

class ReviewsRepositoryImpl(
    private val apiService: ReviewsApiService
) : BaseRepository(), ReviewsRepository {

    override suspend fun getReviews(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<List<Review>> {
        val result = executeApiCall {
            apiService.getReviews(search, ordering, page)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val reviews = ReviewMapper.toDomainList(result.data.getItems())
                NetworkResult.Success(reviews)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun getReviewById(id: Int): NetworkResult<Review> {
        val result = executeApiCall {
            apiService.getReviewById(id)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val review = ReviewMapper.toDomain(result.data)
                NetworkResult.Success(review)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun createReview(
        overallRating: Int,
        accommodationRating: Int?,
        transportRating: Int?,
        guideRating: Int?,
        valueRating: Int?,
        title: String,
        comment: String,
        pros: String?,
        cons: String?,
        bookingId: Int,
        packageId: Int
    ): NetworkResult<Review> {
        val request = CreateReviewRequest(
            overallRating = overallRating,
            accommodationRating = accommodationRating,
            transportRating = transportRating,
            guideRating = guideRating,
            valueRating = valueRating,
            title = title,
            comment = comment,
            pros = pros,
            cons = cons,
            booking = bookingId,
            packageField = packageId
        )

        val result = executeApiCall {
            apiService.createReview(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val review = ReviewMapper.toDomain(result.data)
                NetworkResult.Success(review)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun updateReview(
        id: Int,
        overallRating: Int?,
        accommodationRating: Int?,
        transportRating: Int?,
        guideRating: Int?,
        valueRating: Int?,
        title: String?,
        comment: String?,
        pros: String?,
        cons: String?
    ): NetworkResult<Review> {
        val request = UpdateReviewRequest(
            overallRating = overallRating,
            accommodationRating = accommodationRating,
            transportRating = transportRating,
            guideRating = guideRating,
            valueRating = valueRating,
            title = title,
            comment = comment,
            pros = pros,
            cons = cons
        )

        val result = executeApiCall {
            apiService.updateReview(id, request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val review = ReviewMapper.toDomain(result.data)
                NetworkResult.Success(review)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun deleteReview(id: Int): NetworkResult<Unit> {
        return executeApiCall {
            apiService.deleteReview(id)
        }
    }

    override suspend fun getMyReviews(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<List<Review>> {
        val result = executeApiCall {
            apiService.getMyReviews(search, ordering, page)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val reviews = ReviewMapper.toDomainList(result.data.getItems())
                NetworkResult.Success(reviews)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}