package com.example.travelmarket.logic.data.models.request.inquiries

import com.squareup.moshi.Json

data class UpdateInquiryRequest(
    @Json(name = "message") val message: String?,
    @Json(name = "status") val status: String?
)
