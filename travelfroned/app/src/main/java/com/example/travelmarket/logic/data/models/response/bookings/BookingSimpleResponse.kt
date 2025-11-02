package com.example.travelmarket.logic.data.models.response.bookings

import com.google.gson.annotations.SerializedName

data class BookingSimple(
    val id: Int,
    @SerializedName("booking_number")
    val bookingNumber: String,
    @SerializedName("package_id")
    val packageId: Int,
    @SerializedName("package_name")
    val packageName: String,
    @SerializedName("booking_date")
    val bookingDate: String,
    val status: String
) {
    val displayText: String
        get() = "$packageName - #$bookingNumber"
}

data class BookingsWithoutReviewResponse(
    val exito: Boolean,
    val mensaje: String,
    @SerializedName("reservas")
    val bookings: List<BookingSimple>
)
