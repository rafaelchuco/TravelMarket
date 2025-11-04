package com.example.travelmarket.logic.domain.usecases.wishlist

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.WishlistItem
import com.example.travelmarket.logic.domain.repositories.WishlistRepository
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(): NetworkResult<List<WishlistItem>> {
        return repository.getWishlist()
    }
}

