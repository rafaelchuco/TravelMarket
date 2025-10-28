package com.example.travelmarket.logic.data.models.request.reviews

import com.google.gson.annotations.SerializedName

data class CreateReviewRequest(
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

    @SerializedName("booking")
    val booking: Int,

    @SerializedName("package")
    val packageField: Int
)
