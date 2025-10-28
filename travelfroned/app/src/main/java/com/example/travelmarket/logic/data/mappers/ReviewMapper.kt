package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.reviews.ReviewResponse
import com.example.travelmarket.logic.domain.models.Review

object ReviewMapper {

    fun toDomain(response: ReviewResponse): Review {
        return Review(
            id = response.id,
            overallRating = response.overallRating,
            accommodationRating = response.accommodationRating,
            transportRating = response.transportRating,
            guideRating = response.guideRating,
            valueRating = response.valueRating,
            title = response.title,
            comment = response.comment,
            pros = response.pros,
            cons = response.cons,
            isVerified = response.isVerified,
            isApproved = response.isApproved,
            createdAt = response.createdAt,
            bookingId = response.bookingId,
            customerId = response.customerId,
            packageId = response.packageId
        )
    }

    fun toDomainList(responses: List<ReviewResponse>): List<Review> {
        return responses.map { toDomain(it) }
    }
}