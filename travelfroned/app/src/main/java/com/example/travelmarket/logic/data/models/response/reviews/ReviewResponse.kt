package com.example.travelmarket.logic.data.models.response.reviews

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    val id: Int,
    val booking: Int,
    val customer: Int,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("package")
    val packageId: Int,
    @SerializedName("package_name")
    val packageName: String,
    @SerializedName("overall_rating")
    val overallRating: Int,
    @SerializedName("accommodation_rating")
    val accommodationRating: Int?,
    @SerializedName("transport_rating")
    val transportRating: Int?,
    @SerializedName("guide_rating")
    val guideRating: Int?,
    @SerializedName("value_rating")
    val valueRating: Int?,
    @SerializedName("average_rating")
    val averageRating: Double,
    val title: String,
    val comment: String,
    val pros: String?,
    val cons: String?,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("is_approved")
    val isApproved: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)

// Wrapper para el endpoint my-reviews
data class MyReviewsResponse(
    val exito: Boolean,
    val mensaje: String,
    @SerializedName("resenas")
    val reviews: List<ReviewResponse>
)
