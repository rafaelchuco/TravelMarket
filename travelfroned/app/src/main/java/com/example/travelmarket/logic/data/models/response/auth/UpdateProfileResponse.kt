package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: UserResponse
)