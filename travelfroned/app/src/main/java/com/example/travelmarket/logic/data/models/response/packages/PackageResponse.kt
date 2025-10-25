package com.example.travelmarket.logic.data.models.response.packages

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PackageResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "slug") val slug: String?,
    @Json(name = "short_description") val shortDescription: String?,  // ✅ ESTE VIENE EN EL JSON
    @Json(name = "duration_days") val durationDays: Int?,
    @Json(name = "duration_nights") val durationNights: Int?,
    @Json(name = "price_adult") val priceAdult: Double?,
    @Json(name = "price_child") val priceChild: Double?,
    @Json(name = "max_people") val maxPeople: Int?,
    @Json(name = "min_people") val minPeople: Int?,
    @Json(name = "image") val image: String?,
    @Json(name = "is_featured") val isFeatured: Boolean?,
    @Json(name = "created_at") val createdAt: String?,

    // ✅ USA LOS NOMBRES QUE VIENEN EN EL JSON
    @Json(name = "category_name") val categoryName: String?,
    @Json(name = "destination_name") val destinationName: String?,

    @Json(name = "includes_flight") val includesFlight: Boolean?,
    @Json(name = "includes_guide") val includesGuide: Boolean?,
    @Json(name = "includes_hotel") val includesHotel: Boolean?,
    @Json(name = "includes_meals") val includesMeals: Boolean?,
    @Json(name = "includes_transport") val includesTransport: Boolean?
)
