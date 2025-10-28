package com.example.travelmarket.logic.domain.models

data class Activity(
    val id: Int,
    val name: String,
    val activityType: String,
    val description: String,
    val durationHours: Double,
    val difficultyLevel: String,
    val pricePerPerson: Double,
    val maxGroupSize: Int,
    val image: String?,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val destinationId: Int
)