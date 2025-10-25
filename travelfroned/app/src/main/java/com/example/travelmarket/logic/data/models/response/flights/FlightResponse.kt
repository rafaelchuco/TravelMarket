package com.example.travelmarket.logic.data.models.response.flights

import com.squareup.moshi.Json

// ✅ Wrapper para respuesta paginada
data class FlightsApiResponse(
    @Json(name = "count") val count: Int?,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<FlightResponse>?
)

// ✅ Modelo individual
data class FlightResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "airline") val airline: String?,
    @Json(name = "flight_number") val flightNumber: String?,
    @Json(name = "origin") val origin: String?,
    @Json(name = "destination") val destination: String?,
    @Json(name = "departure_date") val departureDate: String?,
    @Json(name = "arrival_date") val arrivalDate: String?,
    @Json(name = "price") val price: String?,  // ✅ String porque Django lo devuelve así
    @Json(name = "available_seats") val availableSeats: Int?,
    @Json(name = "created_at") val createdAt: String?
)
