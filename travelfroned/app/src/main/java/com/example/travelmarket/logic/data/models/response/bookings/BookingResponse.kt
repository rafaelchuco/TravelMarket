package com.example.travelmarket.logic.data.models.response.bookings

import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("booking_number")
    val bookingNumber: String,

    @SerializedName("customer_id")
    val customerId: Int,

    @SerializedName("travel_date")
    val travelDate: String?,

    @SerializedName("return_date")
    val returnDate: String?,

    @SerializedName("num_adults")
    val numAdults: Int,

    @SerializedName("num_children")
    val numChildren: Int,

    @SerializedName("num_infants")
    val numInfants: Int,

    @SerializedName("total_amount")
    val totalAmount: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("payment_status")
    val paymentStatus: String,

    @SerializedName("booking_date")
    val bookingDate: String
)