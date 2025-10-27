package com.example.travelmarket.logic.data.models.request.inquiries

import com.google.gson.annotations.SerializedName

data class CreateInquiryRequest(
    @SerializedName("user_id") val userId: Long?,
    @SerializedName("email") val email: String,
    @SerializedName("message") val message: String,
    @SerializedName("package_id") val packageId: Long?
)
