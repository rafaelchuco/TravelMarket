package com.example.travelmarket.logic.data.models.request.wishlist

import com.google.gson.annotations.SerializedName

data class AddToWishlistRequest(
    @SerializedName("package") val packageId: Long
)

