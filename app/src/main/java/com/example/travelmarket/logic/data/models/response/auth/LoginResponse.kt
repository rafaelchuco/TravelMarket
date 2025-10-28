package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access")
    val accessToken: String,

    @SerializedName("refresh")
    val refreshToken: String,

    @SerializedName("user")
    val user: UserResponse?
)