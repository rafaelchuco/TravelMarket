package com.example.travelmarket.logic.data.models.response.promotions

import com.google.gson.annotations.SerializedName

data class CouponsWrapperResponse(
    @SerializedName("exito")
    val success: Boolean,

    @SerializedName("mensaje")
    val message: String,

    @SerializedName("cupones")
    val cupones: List<PromotionResponse>
)