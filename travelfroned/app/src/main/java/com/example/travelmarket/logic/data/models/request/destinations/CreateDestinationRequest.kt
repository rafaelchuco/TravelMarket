package com.example.travelmarket.logic.data.models.request.destinations

import com.squareup.moshi.Json

data class CreateDestinationRequest(
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String,
    @Json(name = "description") val description: String?,
    @Json(name = "image_url") val imageUrl: String? = null
)
