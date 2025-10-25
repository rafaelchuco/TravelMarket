package com.example.travelmarket.logic.data.models.response.packages

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryPaginatedDto(
    @Json(name = "count")
    val count: Int,

    @Json(name = "next")
    val next: String?,

    @Json(name = "previous")
    val previous: String?,

    @Json(name = "results")
    val results: List<CategoryResponse>
)
