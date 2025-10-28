package com.example.travelmarket.logic.data.models.request.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh")
    val refreshToken: String
)