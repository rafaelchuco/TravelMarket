package com.example.travelmarket.logic.data.models.response.packages

import com.google.gson.annotations.SerializedName

// ✅ SIN Deserializer, solo camelCase directo
data class PackageResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("durationDays") val durationDays: Int?,
    @SerializedName("durationNights") val durationNights: Int?,
    @SerializedName("priceAdult") val priceAdult: String?,
    @SerializedName("priceChild") val priceChild: String?,
    @SerializedName("maxPeople") val maxPeople: Int?,
    @SerializedName("minPeople") val minPeople: Int?,
    @SerializedName("image") val image: String?,
    @SerializedName("isFeatured") val isFeatured: Boolean?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("categoryName") val categoryName: String?,
    @SerializedName("destinationName") val destinationName: String?,
    @SerializedName("includesFlight") val includesFlight: Boolean?,
    @SerializedName("includesGuide") val includesGuide: Boolean?,
    @SerializedName("includesHotel") val includesHotel: Boolean?,
    @SerializedName("includesMeals") val includesMeals: Boolean?,
    @SerializedName("includesTransport") val includesTransport: Boolean?
)

data class PackagesApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: PackagesResultWrapper?
)

data class PackagesResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("paquetes") val paquetes: List<PackageResponse>?
)
