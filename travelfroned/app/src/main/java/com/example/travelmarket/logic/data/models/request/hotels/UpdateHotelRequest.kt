package com.example.travelmarket.logic.data.models.request.hotels

import com.squareup.moshi.Json

data class UpdateHotelRequest(
    @Json(name = "name") val name: String?,
    @Json(name = "city") val city: String?,
    @Json(name = "address") val address: String?,
    @Json(name = "stars") val stars: Int?,
    @Json(name = "price_per_night") val pricePerNight: Double?,
    @Json(name = "available_rooms") val availableRooms: Int?,
    @Json(name = "image_url") val imageUrl: String?
)
