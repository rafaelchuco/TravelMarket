package com.example.travelmarket.logic.data.models.request.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("password_confirm")
    val passwordConfirm: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("nationality")
    val nationality: String? = null,

    @SerializedName("passport_number")
    val passportNumber: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("country")
    val country: String? = null
)