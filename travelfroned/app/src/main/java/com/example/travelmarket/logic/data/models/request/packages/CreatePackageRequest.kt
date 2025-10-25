package com.example.travelmarket.logic.data.models.request.packages
import com.squareup.moshi.Json

data class CreatePackageRequest(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String?,
    @Json(name = "price") val price: Double,
    @Json(name = "duration_days") val durationDays: Int,
    @Json(name = "category_id") val categoryId: Long?,
    @Json(name = "destination_id") val destinationId: Long?,
    @Json(name = "image_url") val imageUrl: String?
)
