package com.example.travelmarket.logic.domain.models

data class Destination(
    val id: Int,
    val name: String,
    val country: String,
    val continent: String,
    val description: String,
    val shortDescription: String,
    val latitude: String,
    val longitude: String,
    val image: String?,
    val isPopular: Boolean,
    val bestSeason: String,
    val createdAt: String
)