package com.example.travelmarket.logic.data.models.response.bookings


import com.google.gson.annotations.SerializedName

data class CreateBookingResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("booking")
    val booking: BookingDetailResponse
)
