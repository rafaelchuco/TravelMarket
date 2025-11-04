package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.WishlistMapper
import com.example.travelmarket.logic.data.models.request.wishlist.AddToWishlistRequest
import com.example.travelmarket.logic.data.remote.wishlist.WishlistApiService
import com.example.travelmarket.logic.domain.models.WishlistItem
import com.example.travelmarket.logic.domain.repositories.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val api: WishlistApiService
) : WishlistRepository {

    override suspend fun getWishlist(): NetworkResult<List<WishlistItem>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val wishlistItems = body.favoritos?.mapNotNull { 
                    WishlistMapper.toDomain(it) 
                } ?: emptyList()
                NetworkResult.Success(wishlistItems)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            android.util.Log.e("WISHLIST_REPO_ERROR", "Exception: ${e.message}", e)
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun addToWishlist(packageId: Long): NetworkResult<WishlistItem> {
        return try {
            val response = api.add(AddToWishlistRequest(packageId))
            if (response.isSuccessful && response.body() != null) {
                val wishlistItem = WishlistMapper.toDomain(response.body()!!)
                if (wishlistItem != null) {
                    NetworkResult.Success(wishlistItem)
                } else {
                    NetworkResult.Error("Invalid wishlist data")
                }
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun removeFromWishlist(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
