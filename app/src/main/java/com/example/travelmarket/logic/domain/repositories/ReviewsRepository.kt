package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review

interface ReviewsRepository {

    suspend fun getReviews(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Review>>

    suspend fun getReviewById(id: Int): NetworkResult<Review>

    suspend fun createReview(
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
    ): NetworkResult<Review>

    suspend fun updateReview(
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
    ): NetworkResult<Review>

    suspend fun deleteReview(id: Int): NetworkResult<Unit>

    suspend fun getMyReviews(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Review>>
}