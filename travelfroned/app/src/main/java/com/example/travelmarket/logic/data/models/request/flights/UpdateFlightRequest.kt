package com.example.travelmarket.logic.data.models.request.flights

import com.squareup.moshi.Json

data class UpdateFlightRequest(
    @Json(name = "airline") val airline: String?,
    @Json(name = "flight_number") val flightNumber: String?,
    @Json(name = "origin") val origin: String?,
    @Json(name = "destination") val destination: String?,
    @Json(name = "departure_date") val departureDate: String?,
    @Json(name = "arrival_date") val arrivalDate: String?,
    @Json(name = "price") val price: Double?,
    @Json(name = "available_seats") val availableSeats: Int?
)
