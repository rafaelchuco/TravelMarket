package com.example.travelmarket.logic.data.models.request.packages

import com.google.gson.annotations.SerializedName

data class CreatePackageRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("duration_days") val durationDays: Int,
    @SerializedName("category_id") val categoryId: Long?,
    @SerializedName("destination_id") val destinationId: Long?,
    @SerializedName("image_url") val imageUrl: String?
)
