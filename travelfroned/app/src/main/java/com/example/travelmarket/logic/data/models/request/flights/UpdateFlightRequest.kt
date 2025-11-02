package com.example.travelmarket.logic.data.models.request.flights

import com.google.gson.annotations.SerializedName

data class UpdateFlightRequest(
    @SerializedName("airline") val airline: String?,
    @SerializedName("flight_number") val flightNumber: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("destination") val destination: String?,
    @SerializedName("departure_date") val departureDate: String?,
    @SerializedName("arrival_date") val arrivalDate: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("available_seats") val availableSeats: Int?
)
