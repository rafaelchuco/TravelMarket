package com.example.travelmarket.logic.data.models.request.auth

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("nationality")
    val nationality: String?,

    @SerializedName("passport_number")
    val passportNumber: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("country")
    val country: String?
)