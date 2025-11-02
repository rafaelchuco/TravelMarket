package com.example.travelmarket.logic.data.models.response.reviews

import com.google.gson.annotations.SerializedName

data class ReviewsWrapperResponse(
    @SerializedName("exito")
    val success: Boolean,

    @SerializedName("mensaje")
    val message: String,

    @SerializedName("resenas")
    val resenas: List<ReviewResponse>
)