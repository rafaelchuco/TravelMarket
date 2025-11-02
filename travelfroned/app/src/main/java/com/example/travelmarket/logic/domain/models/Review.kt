package com.example.travelmarket.logic.domain.models

import com.google.gson.annotations.SerializedName

data class Review(
    val id: Int,
    val booking: Int,
    val customer: Int,
    @SerializedName("customer_name")
    val customerName: String?,  // ← nullable
    @SerializedName("package")
    val packageId: Int,
    @SerializedName("package_name")
    val packageName: String?,  // ← nullable
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
    val title: String?,  // ← nullable
    val comment: String?,  // ← nullable
    val pros: String?,
    val cons: String?,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("is_approved")
    val isApproved: Boolean,
    @SerializedName("created_at")
    val createdAt: String?  // ← nullable
) {
    val bookingId: Int get() = booking
    val customerId: Int get() = customer
}
