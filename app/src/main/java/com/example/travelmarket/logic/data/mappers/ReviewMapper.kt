package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.reviews.ReviewResponse
import com.example.travelmarket.logic.domain.models.Review

object ReviewMapper {

    fun toDomain(response: ReviewResponse): Review {
        return Review(
            id = response.id,
            overallRating = response.overallRating ?: 0,
            accommodationRating = response.accommodationRating,
            transportRating = response.transportRating,
            guideRating = response.guideRating,
            valueRating = response.valueRating,
            title = response.title ?: "",
            comment = response.comment ?: "",
            pros = response.pros,
            cons = response.cons,
            isVerified = response.isVerified ?: false,
            isApproved = response.isApproved ?: false,
            createdAt = response.createdAt ?: "",
            bookingId = response.bookingId ?: 0,
            customerId = response.customerId ?: 0,
            packageId = response.packageId ?: 0,
            customerName = response.customerName,
            packageName = response.packageName,
            averageRating = response.averageRating
        )
    }

    fun toDomainList(responses: List<ReviewResponse>): List<Review> {
        return responses.map { toDomain(it) }
    }
}