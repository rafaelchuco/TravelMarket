package com.example.travelmarket.logic.domain.repositories
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.hotels.CreateHotelRequest
import com.example.travelmarket.logic.domain.models.Hotel

interface HotelsRepository {
    suspend fun getHotels(): NetworkResult<List<Hotel>>
    suspend fun getHotelById(id: Long): NetworkResult<Hotel>
    suspend fun createHotel(request: CreateHotelRequest): NetworkResult<Hotel>
    suspend fun updateHotel(id: Long, request: CreateHotelRequest): NetworkResult<Hotel>
    suspend fun deleteHotel(id: Long): NetworkResult<Unit>
}


/**
 * Repositorio de hoteles.
 *
 * USO EN APP MÓVIL USUARIO:
 *   - getHotels()        // ✅ Listar hoteles (GET)
 *   - getHotelById()     // ✅ Detalle hotel (GET)
 *
 * SOLO ADMIN (NO USAR EN VISTAS USUARIO):
 *   - createHotel()
 *   - updateHotel()
 *   - deleteHotel()
 */