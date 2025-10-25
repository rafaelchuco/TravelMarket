package com.example.travelmarket.logic.data.models.request.inquiries

import com.squareup.moshi.Json

data class CreateInquiryRequest(
    @Json(name = "user_id") val userId: Long?,
    @Json(name = "email") val email: String,
    @Json(name = "message") val message: String,
    @Json(name = "package_id") val packageId: Long?
)
