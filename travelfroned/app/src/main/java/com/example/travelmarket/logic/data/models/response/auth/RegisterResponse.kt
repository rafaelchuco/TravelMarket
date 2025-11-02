package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("exito")  // ✅ Cambiado
    val success: Boolean,

    @SerializedName("mensaje")  // ✅ Cambiado de "message" a "mensaje"
    val message: String,

    @SerializedName("usuario")  // ✅ Cambiado de "user" a "usuario"
    val user: UserResponse
)