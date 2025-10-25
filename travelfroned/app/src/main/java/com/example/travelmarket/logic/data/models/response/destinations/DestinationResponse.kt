package com.example.travelmarket.logic.data.models.response.destinations

import com.squareup.moshi.Json

// ✅ Wrapper para la respuesta paginada
data class DestinationsApiResponse(
    @Json(name = "count") val count: Int?,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<DestinationResponse>?
)

// ✅ Modelo individual
data class DestinationResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "country") val country: String?,
    @Json(name = "continent") val continent: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "short_description") val shortDescription: String?,
    @Json(name = "latitude") val latitude: String?,
    @Json(name = "longitude") val longitude: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "is_popular") val isPopular: Boolean?,
    @Json(name = "best_season") val bestSeason: String?,
    @Json(name = "created_at") val createdAt: String?
)
