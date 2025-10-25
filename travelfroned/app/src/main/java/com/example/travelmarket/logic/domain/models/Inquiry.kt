package com.example.travelmarket.logic.domain.models

data class Inquiry(
    val id: Long,
    val userId: Long?,
    val email: String,
    val message: String,
    val packageId: Long?,
    val status: String,
    val createdAt: String
)
