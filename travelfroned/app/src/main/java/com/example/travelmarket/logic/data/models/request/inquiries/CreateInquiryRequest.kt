package com.example.travelmarket.logic.data.models.request.inquiries

import com.google.gson.annotations.SerializedName

data class CreateInquiryRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("subject") val subject: String,
    @SerializedName("message") val message: String,
    @SerializedName("package") val packageId: Long?
)
