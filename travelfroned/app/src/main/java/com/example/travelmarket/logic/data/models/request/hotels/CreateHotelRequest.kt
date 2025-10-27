package com.example.travelmarket.logic.data.models.request.hotels

import com.google.gson.annotations.SerializedName

data class CreateHotelRequest(
    @SerializedName("name") val name: String,
    @SerializedName("city") val city: String,
    @SerializedName("address") val address: String?,
    @SerializedName("stars") val stars: Int,
    @SerializedName("price_per_night") val pricePerNight: Double,
    @SerializedName("available_rooms") val availableRooms: Int?,
    @SerializedName("image_url") val imageUrl: String?
)
