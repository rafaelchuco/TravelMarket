package com.example.travelmarket.logic.data.models.response.promotions

import com.google.gson.annotations.SerializedName

data class PromotionResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("discount_type")
    val discountType: String,

    @SerializedName("discount_value")
    val discountValue: String,

    @SerializedName("min_purchase_amount")
    val minPurchaseAmount: String?,

    @SerializedName("max_discount_amount")
    val maxDiscountAmount: String?,

    @SerializedName("valid_from")
    val startDate: String,

    @SerializedName("valid_until")
    val endDate: String,

    @SerializedName("max_uses")
    val maxUses: Int?,

    @SerializedName("times_used")
    val timesUsed: Int,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("created_at")
    val createdAt: String
)