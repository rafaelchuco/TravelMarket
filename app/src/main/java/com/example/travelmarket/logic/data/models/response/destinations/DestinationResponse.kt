package com.example.travelmarket.logic.data.models.response.destinations

import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("continent")
    val continent: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("short_description")
    val shortDescription: String,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("is_popular")
    val isPopular: Boolean,

    @SerializedName("best_season")
    val bestSeason: String,

    @SerializedName("created_at")
    val createdAt: String
)