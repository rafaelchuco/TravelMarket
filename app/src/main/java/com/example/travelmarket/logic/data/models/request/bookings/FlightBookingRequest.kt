package com.example.travelmarket.logic.data.models.request.bookings

import com.google.gson.annotations.SerializedName

data class FlightBookingRequest(
    @SerializedName("flight_id")
    val flightId: Int?,

    @SerializedName("booking_type")
    val bookingType: String?,

    @SerializedName("num_passengers")
    val numPassengers: Int?,

    @SerializedName("seat_numbers")
    val seatNumbers: String?,

    @SerializedName("price_per_person")
    val pricePerPerson: String?,

    @SerializedName("total_price")
    val totalPrice: String?,

    @SerializedName("pnr_number")
    val pnrNumber: String?
)