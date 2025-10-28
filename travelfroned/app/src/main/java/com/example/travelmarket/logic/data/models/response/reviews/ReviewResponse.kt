package com.example.travelmarket.logic.data.models.response.reviews
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    val id: Int,

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

    @SerializedName("title")
    val title: String,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("pros")
    val pros: String?,

    @SerializedName("cons")
    val cons: String?,

    @SerializedName("is_verified")
    val isVerified: Boolean,

    @SerializedName("is_approved")
    val isApproved: Boolean,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("booking")
    val bookingId: Int,

    @SerializedName("customer")
    val customerId: Int,

    @SerializedName("package")
    val packageId: Int,

    @SerializedName("customer_name")
    val customerName: String?,

    @SerializedName("package_name")
    val packageName: String?,

    @SerializedName("average_rating")
    val averageRating: Double?
)