package com.example.travelmarket.logic.data.models.request.destinations

import com.google.gson.annotations.SerializedName

data class UpdateDestinationRequest(
    @SerializedName("name") val name: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image_url") val imageUrl: String?
)
