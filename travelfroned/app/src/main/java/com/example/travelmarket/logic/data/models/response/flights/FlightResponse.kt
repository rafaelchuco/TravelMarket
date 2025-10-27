package com.example.travelmarket.logic.data.models.response.flights

import com.google.gson.annotations.SerializedName

data class FlightsApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: FlightsResultWrapper?  // ✅ Cambio
)

// ✅ NUEVO: Wrapper para el objeto "results"
data class FlightsResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("vuelos") val vuelos: List<FlightResponse>?  // ✅ Probablemente sea "vuelos" (español) o "flights"
)

data class FlightResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("airline") val airline: String?,
    @SerializedName("flight_number") val flightNumber: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("destination") val destination: String?,
    @SerializedName("departure_date") val departureDate: String?,
    @SerializedName("arrival_date") val arrivalDate: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("available_seats") val availableSeats: Int?,
    @SerializedName("created_at") val createdAt: String?
)
