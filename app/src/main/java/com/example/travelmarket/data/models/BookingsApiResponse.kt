package com.example.travelmarket.data.models

import com.example.travelmarket.domain.models.Booking
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookingsApiResponse(
    @Json(name = "count")
    val count: Int,
    
    @Json(name = "next")
    val next: String?,
    
    @Json(name = "previous")
    val previous: String?,
    
    @Json(name = "results")
    val results: List<Booking>
)


