package com.example.travelmarket.logic.data.models.request.packages

import com.squareup.moshi.Json

data class CreateCategoryRequest(
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?
)
