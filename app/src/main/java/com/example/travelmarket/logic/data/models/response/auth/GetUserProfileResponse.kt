package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class GetUserProfileResponse(
    @SerializedName("exito")
    val success: Boolean,

    @SerializedName("mensaje")
    val message: String,

    @SerializedName("usuario")
    val user: UserResponse
)