package com.example.travelmarket.logic.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

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
    val country: String?,

    @SerializedName("user_type")
    val userType: String,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("is_staff")
    val isStaff: Boolean,

    @SerializedName("is_superuser")
    val isSuperuser: Boolean,

    @SerializedName("last_login")
    val lastLogin: String?,

    @SerializedName("date_joined")
    val dateJoined: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)