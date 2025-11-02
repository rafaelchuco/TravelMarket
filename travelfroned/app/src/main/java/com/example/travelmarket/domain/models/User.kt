package com.example.travelmarket.domain.models

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val nationality: String?,
    val passportNumber: String?,
    val address: String?,
    val city: String?,
    val country: String?,
    val userType: String,
    val isActive: Boolean,
    val isStaff: Boolean,
    val isSuperuser: Boolean,
    val lastLogin: String?,
    val dateJoined: String,
    val createdAt: String,
    val updatedAt: String
)
