package com.example.travelmarket.logic.data.models.response.activities

import com.google.gson.annotations.SerializedName

data class ActivityResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("activity_type")
    val activityType: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("duration_hours")
    val durationHours: Double,

    @SerializedName("difficulty_level")
    val difficultyLevel: String,

    @SerializedName("price_per_person")
    val pricePerPerson: String,

    @SerializedName("max_group_size")
    val maxGroupSize: Int,

    @SerializedName("image")
    val image: String?,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("destination")
    val destination: Int
)