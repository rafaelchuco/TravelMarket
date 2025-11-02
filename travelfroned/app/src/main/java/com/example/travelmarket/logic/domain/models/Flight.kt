package com.example.travelmarket.logic.domain.models

data class Flight(
    val id: Long,
    val airline: String,
    val flightNumber: String,
    val origin: String,
    val destination: String,
    val departureDate: String,
    val arrivalDate: String,
    val price: Double,
    val availableSeats: Int,
    val createdAt: String
)
