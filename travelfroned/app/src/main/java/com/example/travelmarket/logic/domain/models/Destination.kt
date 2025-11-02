package com.example.travelmarket.logic.domain.models

data class Destination(
    val id: Long,
    val name: String,
    val country: String,
    val continent: String,
    val description: String,
    val shortDescription: String,
    val latitude: String,
    val longitude: String,
    val imageUrl: String,
    val isPopular: Boolean,
    val bestSeason: String,
    val createdAt: String
)
