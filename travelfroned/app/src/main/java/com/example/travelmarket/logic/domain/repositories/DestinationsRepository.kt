package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.data.models.request.destinations.UpdateDestinationRequest
import com.example.travelmarket.logic.domain.models.Destination

interface DestinationsRepository {
    suspend fun getDestinations(): NetworkResult<List<Destination>>
    suspend fun getDestinationById(id: Long): NetworkResult<Destination>
    suspend fun createDestination(request: CreateDestinationRequest): NetworkResult<Destination>
    suspend fun updateDestination(id: Long, request: UpdateDestinationRequest): NetworkResult<Destination>
    suspend fun deleteDestination(id: Long): NetworkResult<Unit>
}


/**
 * Repositorio de destinos turísticos.
 *
 * USO EN APP MÓVIL:
 *   - getDestinations()     // ✅ Listar destinos (GET) - USADO POR USUARIO
 *   - getDestinationById()  // ✅ Detalle de destino (GET) - USADO POR USUARIO
 *
 * SOLO PARA ADMIN (NO IMPLEMENTAR EN VISTAS DE USUARIO):
 *   - createDestination()   // (POST)
 *   - updateDestination()   // (PUT/PATCH)
 *   - deleteDestination()   // (DELETE)
 *
 * Estos métodos existen para arquitectura limpia y uso futuro/admin, pero NO deben usarse en la app del usuario.
 */