package com.example.travelmarket.logic.data.models.request.bookings

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("package_id")
    val packageId: Int,

    @SerializedName("travel_date")
    val travelDate: String,

    @SerializedName("return_date")
    val returnDate: String?,

    @SerializedName("num_adults")
    val numAdults: Int,

    @SerializedName("num_children")
    val numChildren: Int,

    @SerializedName("num_infants")
    val numInfants: Int,

    @SerializedName("special_requests")
    val specialRequests: String?

    // ❌ NO ENVIAR: booking_number, subtotal, discount_amount, tax_amount,
    //               total_amount, paid_amount, status, payment_status
    // El backend los calcula automáticamente
)
