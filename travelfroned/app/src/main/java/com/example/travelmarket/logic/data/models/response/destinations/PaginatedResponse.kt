package com.example.travelmarket.logic.data.models.response.destinations

import com.squareup.moshi.Json

data class PaginatedResponse<T>(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<T>
)
