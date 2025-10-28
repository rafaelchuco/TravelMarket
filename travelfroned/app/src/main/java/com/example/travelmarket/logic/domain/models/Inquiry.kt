package com.example.travelmarket.logic.domain.models

data class Inquiry(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String?,
    val subject: String,
    val message: String,
    val packageId: Long?,
    val status: String,
    val adminResponse: String?,
    val createdAt: String,
    val updatedAt: String?
)
