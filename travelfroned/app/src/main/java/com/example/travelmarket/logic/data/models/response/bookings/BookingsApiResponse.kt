package com.example.travelmarket.logic.data.models.response.bookings

import com.google.gson.annotations.SerializedName

// Para getBookings()
data class BookingsApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: BookingsResultWrapper?
)

data class BookingsResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("reservas") val reservas: List<BookingResponse>?
)

// Para getMyBookings()
data class MyBookingsApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: MyBookingsResultWrapper?
)

data class MyBookingsResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("reservas") val reservas: List<BookingDetailResponse>?
)
