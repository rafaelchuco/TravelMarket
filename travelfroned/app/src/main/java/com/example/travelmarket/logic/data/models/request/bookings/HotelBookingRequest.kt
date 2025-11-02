package com.example.travelmarket.logic.data.models.request.bookings

import com.google.gson.annotations.SerializedName

data class HotelBookingRequest(
    @SerializedName("hotel_id")
    val hotelId: Int?,

    @SerializedName("check_in_date")
    val checkInDate: String,

    @SerializedName("check_out_date")
    val checkOutDate: String,

    @SerializedName("num_rooms")
    val numRooms: Int?,

    @SerializedName("room_type")
    val roomType: String?,

    @SerializedName("price_per_night")
    val pricePerNight: String?,

    @SerializedName("total_nights")
    val totalNights: Int?,

    @SerializedName("total_price")
    val totalPrice: String?,

    @SerializedName("confirmation_number")
    val confirmationNumber: String?
)