package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.flights.FlightResponse
import com.example.travelmarket.logic.domain.models.Flight

object FlightMapper {
    fun toDomain(response: FlightResponse): Flight {
        return Flight(
            id = response.id,
            airline = response.airline ?: "Sin aerolínea",  // ✅ AGREGADO ?:
            flightNumber = response.flightNumber ?: "",  // ✅ AGREGADO ?:
            origin = response.origin ?: "",  // ✅ AGREGADO ?:
            destination = response.destination ?: "",  // ✅ AGREGADO ?:
            departureDate = response.departureDate ?: "",  // ✅ AGREGADO ?:
            arrivalDate = response.arrivalDate ?: "",
            price = response.price?.toDoubleOrNull() ?: 0.0,  // ✅ CONVERTIR String? a Double
            availableSeats = response.availableSeats ?: 0,
            createdAt = response.createdAt ?: ""
        )
    }

    fun toDomainList(responses: List<FlightResponse>): List<Flight> {
        return responses.map { toDomain(it) }
    }
}
