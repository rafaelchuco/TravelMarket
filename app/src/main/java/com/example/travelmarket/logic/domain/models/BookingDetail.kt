package com.example.travelmarket.logic.domain.models

data class BookingDetail(
    val id: Int,
    val bookingNumber: String,
    val travelDate: String?,
    val returnDate: String?,
    val numAdults: Int,
    val numChildren: Int,
    val numInfants: Int,
    val subtotal: String,
    val discountAmount: String,
    val taxAmount: String,
    val totalAmount: String,
    val paidAmount: String,
    val status: String,
    val paymentStatus: String,
    val specialRequests: String?,
    val bookingDate: String,
    val updatedAt: String,
    val customer: Int,
    val packageId: Int?
)