package com.example.travelmarket.logic.domain.models

data class Review(
    val id: Int,
    val overallRating: Int,
    val accommodationRating: Int?,
    val transportRating: Int?,
    val guideRating: Int?,
    val valueRating: Int?,
    val title: String,
    val comment: String,
    val pros: String?,
    val cons: String?,
    val isVerified: Boolean,
    val isApproved: Boolean,
    val createdAt: String,
    val bookingId: Int,
    val customerId: Int,
    val packageId: Int,
    val customerName: String?,
    val packageName: String?,
    val averageRating: Double?
)