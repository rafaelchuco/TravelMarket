package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("exito")  // ✅ AGREGAR
    val success: Boolean,

    @SerializedName("mensaje")  // ✅ AGREGAR
    val message: String,

    @SerializedName("usuario")  // ✅ Cambiar de "user" a "usuario"
    val user: UserResponse
)