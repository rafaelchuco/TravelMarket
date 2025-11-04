package com.example.travelmarket.logic.domain.models

data class WishlistItem(
    val id: Long,
    val userId: Long,
    val packageId: Long,
    val packageName: String,
    val packagePrice: String,
    val addedAt: String
)

