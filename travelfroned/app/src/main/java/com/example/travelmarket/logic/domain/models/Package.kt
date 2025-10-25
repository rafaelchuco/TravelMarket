package com.example.travelmarket.logic.domain.models

data class Package(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val durationDays: Int,
    val categoryId: Long?,
    val destinationId: Long?,
    val imageUrl: String
)
