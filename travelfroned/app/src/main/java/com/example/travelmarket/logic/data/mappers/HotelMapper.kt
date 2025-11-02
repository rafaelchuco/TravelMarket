package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.hotels.HotelResponse
import com.example.travelmarket.logic.domain.models.Hotel

object HotelMapper {
    fun toDomain(response: HotelResponse): Hotel {
        return Hotel(
            id = response.id,
            name = response.name ?: "Sin nombre",
            city = "", // Django no devuelve "city" - puedes extraerlo del address si quieres
            address = response.address ?: "",
            stars = response.starRating ?: 0,  // ✅ CAMBIADO: starRating
            pricePerNight = response.pricePerNight?.toDoubleOrNull() ?: 0.0,  // ✅ CAMBIADO: convertir String a Double
            availableRooms = response.totalRooms ?: 0,  // ✅ CAMBIADO: totalRooms
            imageUrl = response.image ?: "",  // ✅ CAMBIADO: image
            createdAt = "" // Django no devuelve created_at en la lista
        )
    }

    fun toDomainList(responses: List<HotelResponse>): List<Hotel> {
        return responses.map { toDomain(it) }
    }
}
