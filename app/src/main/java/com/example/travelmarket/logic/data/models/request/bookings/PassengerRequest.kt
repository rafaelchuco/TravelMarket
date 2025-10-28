package com.example.travelmarket.logic.data.models.request.bookings

import com.google.gson.annotations.SerializedName

data class PassengerRequest(
    @SerializedName("passenger_type")
    val passengerType: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("passport_number")
    val passportNumber: String?,

    @SerializedName("nationality")
    val nationality: String?
)