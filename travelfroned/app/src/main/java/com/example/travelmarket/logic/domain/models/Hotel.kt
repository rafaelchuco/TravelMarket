package com.example.travelmarket.logic.domain.models

data class Hotel(
    val id: Long,
    val name: String,
    val city: String,
    val address: String,
    val stars: Int,
    val pricePerNight: Double,
    val availableRooms: Int,
    val imageUrl: String,
    val createdAt: String
)
