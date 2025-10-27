package com.example.travelmarket.logic.data.models.request.inquiries

import com.google.gson.annotations.SerializedName

data class UpdateInquiryRequest(
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: String?
)
