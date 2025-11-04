package com.example.travelmarket.logic.domain.usecases.wishlist

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.WishlistItem
import com.example.travelmarket.logic.domain.repositories.WishlistRepository
import javax.inject.Inject

class AddToWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(packageId: Long): NetworkResult<WishlistItem> {
        return repository.addToWishlist(packageId)
    }
}

class RemoveFromWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.removeFromWishlist(id)
    }
}
