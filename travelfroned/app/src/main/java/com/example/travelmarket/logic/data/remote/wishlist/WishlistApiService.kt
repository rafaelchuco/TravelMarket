package com.example.travelmarket.logic.data.remote.wishlist

import com.example.travelmarket.logic.data.models.request.wishlist.AddToWishlistRequest
import com.example.travelmarket.logic.data.models.response.wishlist.WishlistApiResponse
import com.example.travelmarket.logic.data.models.response.wishlist.WishlistItemResponse
import retrofit2.Response
import retrofit2.http.*

interface WishlistApiService {
    @GET("wishlist/")
    suspend fun list(): Response<WishlistApiResponse>

    @POST("wishlist/")
    suspend fun add(@Body body: AddToWishlistRequest): Response<WishlistItemResponse>

    @DELETE("wishlist/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
