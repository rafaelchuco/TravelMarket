package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.wishlist.WishlistItemResponse
import com.example.travelmarket.logic.domain.models.WishlistItem

object WishlistMapper {
    fun toDomain(response: WishlistItemResponse): WishlistItem? {
        return if (response.packageName.isNullOrEmpty()) {
            null
        } else {
            WishlistItem(
                id = response.id,
                userId = response.userId,
                packageId = response.packageId,
                packageName = response.packageName ?: "",
                packagePrice = response.packagePrice ?: "0.00",
                addedAt = response.addedAt ?: ""
            )
        }
    }

    fun toDomainList(responses: List<WishlistItemResponse>): List<WishlistItem> {
        return responses.mapNotNull { toDomain(it) }
    }
}

