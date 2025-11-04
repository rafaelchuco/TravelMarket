package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.WishlistItem

interface WishlistRepository {
    suspend fun getWishlist(): NetworkResult<List<WishlistItem>>
    suspend fun addToWishlist(packageId: Long): NetworkResult<WishlistItem>
    suspend fun removeFromWishlist(id: Long): NetworkResult<Unit>
}

