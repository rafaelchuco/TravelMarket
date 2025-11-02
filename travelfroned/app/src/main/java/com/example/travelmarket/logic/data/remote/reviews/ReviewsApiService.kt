package com.example.travelmarket.logic.data.remote.reviews

import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.models.request.reviews.CreateReviewRequest
import com.example.travelmarket.logic.data.models.request.reviews.UpdateReviewRequest
import com.example.travelmarket.logic.data.models.response.bookings.BookingsWithoutReviewResponse
import com.example.travelmarket.logic.data.models.response.reviews.MyReviewsResponse
import com.example.travelmarket.logic.data.models.response.reviews.ReviewResponse
import retrofit2.Response
import retrofit2.http.*

interface ReviewsApiService {

    @GET("reviews/")
    suspend fun getReviews(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedResponse<ReviewResponse>>

    @GET("reviews/{id}/")
    suspend fun getReviewById(
        @Path("id") id: Int
    ): Response<ReviewResponse>

    @POST("reviews/")
    suspend fun createReview(
        @Body request: CreateReviewRequest
    ): Response<ReviewResponse>

    @PATCH("reviews/{id}/")
    suspend fun updateReview(
        @Path("id") id: Int,
        @Body request: UpdateReviewRequest
    ): Response<ReviewResponse>

    @DELETE("reviews/{id}/")
    suspend fun deleteReview(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("reviews/my-reviews/")
    suspend fun getMyReviews(): Response<MyReviewsResponse>

    @GET("reviews/bookings-without-review/")  // ← CAMBIADO DE review/ a reviews/
    suspend fun getBookingsWithoutReview(): Response<BookingsWithoutReviewResponse>
}
