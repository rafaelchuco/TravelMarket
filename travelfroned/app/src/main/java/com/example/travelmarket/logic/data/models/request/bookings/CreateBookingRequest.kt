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
    val numChildren: Int = 0,

    @SerializedName("num_infants")
    val numInfants: Int = 0,

    @SerializedName("total_amount")
    val totalAmount: Double,  // ✅ AGREGADO - REQUERIDO

    @SerializedName("subtotal")
    val subtotal: Double = 0.0,

    @SerializedName("discount_amount")
    val discountAmount: Double = 0.0,

    @SerializedName("tax_amount")
    val taxAmount: Double = 0.0,

    @SerializedName("paid_amount")
    val paidAmount: Double = 0.0,

    @SerializedName("status")
    val status: String = "pending",

    @SerializedName("payment_status")
    val paymentStatus: String = "unpaid",

    @SerializedName("special_requests")
    val specialRequests: String? = null
)
