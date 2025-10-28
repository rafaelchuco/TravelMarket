package com.example.travelmarket.logic.data.models.response.reviews

import com.google.gson.annotations.SerializedName

data class ReviewsPaginatedResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: ReviewsWrapperResponse
)
