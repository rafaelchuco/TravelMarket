package com.example.travelmarket.logic.data.models.request.bookings

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("booking_number")
    val bookingNumber: String?,

    @SerializedName("package_id")
    val packageId: Int?,

    @SerializedName("travel_date")
    val travelDate: String?,

    @SerializedName("return_date")
    val returnDate: String?,

    @SerializedName("num_adults")
    val numAdults: Int?,

    @SerializedName("num_children")
    val numChildren: Int?,

    @SerializedName("num_infants")
    val numInfants: Int?,

    @SerializedName("subtotal")
    val subtotal: String?,

    @SerializedName("discount_amount")
    val discountAmount: String?,

    @SerializedName("tax_amount")
    val taxAmount: String?,

    @SerializedName("total_amount")
    val totalAmount: String?,

    @SerializedName("paid_amount")
    val paidAmount: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("payment_status")
    val paymentStatus: String?,

    @SerializedName("special_requests")
    val specialRequests: String?,

    @SerializedName("passengers")
    val passengers: List<PassengerRequest>?,

    @SerializedName("hotel_bookings")
    val hotelBookings: List<HotelBookingRequest>?,

    @SerializedName("flight_bookings")
    val flightBookings: List<FlightBookingRequest>?
)