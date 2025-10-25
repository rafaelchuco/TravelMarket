package com.example.travelmarket.logic.data.models.response.hotels

import com.squareup.moshi.Json

// ✅ Wrapper para la respuesta paginada de Django REST Framework
data class HotelsApiResponse(
    @Json(name = "count") val count: Int?,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<HotelResponse>?  // ✅ CAMBIADO: "data" → "results"
)

// ✅ Modelo individual de hotel
data class HotelResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "destination") val destination: Any?,  // puede ser null
    @Json(name = "address") val address: String?,
    @Json(name = "star_rating") val starRating: Int?,  // ✅ CAMBIADO
    @Json(name = "description") val description: String?,
    @Json(name = "amenities") val amenities: String?,
    @Json(name = "check_in_time") val checkInTime: String?,
    @Json(name = "check_out_time") val checkOutTime: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "price_per_night") val pricePerNight: String?,  // Django devuelve como String
    @Json(name = "total_rooms") val totalRooms: Int?,
    @Json(name = "image") val image: String?,
    @Json(name = "is_active") val isActive: Boolean?
)
