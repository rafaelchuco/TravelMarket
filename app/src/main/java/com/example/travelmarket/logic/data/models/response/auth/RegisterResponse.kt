package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("exito")  // ✅ AGREGAR
    val success: Boolean,

    @SerializedName("mensaje")  // ✅ Cambiar de "message" a "mensaje"
    val message: String,

    @SerializedName("usuario")  // ✅ Cambiar de "user" a "usuario"
    val user: UserResponse
)