package com.example.travelmarket.logic.domain.repositories
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.flights.CreateFlightRequest
import com.example.travelmarket.logic.domain.models.Flight

interface FlightsRepository {
    suspend fun getFlights(): NetworkResult<List<Flight>>
    suspend fun getFlightById(id: Long): NetworkResult<Flight>
    suspend fun createFlight(request: CreateFlightRequest): NetworkResult<Flight>
    suspend fun updateFlight(id: Long, request: CreateFlightRequest): NetworkResult<Flight>
    suspend fun deleteFlight(id: Long): NetworkResult<Unit>
}


/**
 * Repositorio de vuelos.
 *
 * USO EN APP MÓVIL:
 *   - getFlights()        // ✅ Listar vuelos (GET)
 *   - getFlightById()     // ✅ Detalle de vuelo (GET)
 *
 * SOLO PARA ADMIN (NO USAR EN VISTAS USUARIO):
 *   - createFlight()
 *   - updateFlight()
 *   - deleteFlight()
 */