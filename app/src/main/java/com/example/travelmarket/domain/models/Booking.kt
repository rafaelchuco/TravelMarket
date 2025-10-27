package com.example.travelmarket.domain.models

data class Booking(
    val id: Int,
    val bookingNumber: String,
    val customerId: String?,
    val travelDate: String?,
    val returnDate: String?,
    val numAdults: Int,
    val numChildren: Int,
    val numInfants: Int,
    val totalAmount: String,
    val status: String,
    val paymentStatus: String,
    val bookingDate: String
)
