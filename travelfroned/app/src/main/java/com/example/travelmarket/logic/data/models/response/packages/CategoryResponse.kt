package com.example.travelmarket.logic.data.models.response.packages

import com.squareup.moshi.Json

// ✅ Wrapper para respuesta paginada
data class CategoriesApiResponse(
    @Json(name = "count") val count: Int?,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<CategoryResponse>?
)

// ✅ Modelo individual
data class CategoryResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "created_at") val createdAt: String?
)
