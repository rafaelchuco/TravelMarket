package com.example.travelmarket.logic.data.models.response.bookings

import com.google.gson.annotations.SerializedName

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

data class MyBookingsApiResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("reservas") val reservas: List<BookingResponse>?
)