package com.example.travelmarket.logic.data.models.response.bookings

import com.google.gson.annotations.SerializedName

data class CreateBookingResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("numero_reserva") val numeroReserva: String?,
    @SerializedName("detalles") val detalles: BookingDetailResponse  // ✅ Era "booking", ahora "detalles"
)
