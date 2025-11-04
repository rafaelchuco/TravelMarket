package com.example.travelmarket.logic.data.models.response.wishlist

import com.google.gson.annotations.SerializedName

// Respuesta directa del backend (sin paginación)
data class WishlistApiResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("favoritos") val favoritos: List<WishlistItemResponse>?
)

data class WishlistItemResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("user") val userId: Long,
    @SerializedName("package") val packageId: Long,
    @SerializedName("package_name") val packageName: String?,
    @SerializedName("package_price") val packagePrice: String?,
    @SerializedName("added_at") val addedAt: String?
)
