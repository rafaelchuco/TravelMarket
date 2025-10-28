package com.example.travelmarket.logic.domain.models

data class Promotion(
    val id: Int,
    val code: String,
    val description: String,
    val discountType: String,
    val discountValue: String,
    val minPurchaseAmount: String?,
    val maxDiscountAmount: String?,
    val startDate: String,
    val endDate: String,
    val isActive: Boolean
)