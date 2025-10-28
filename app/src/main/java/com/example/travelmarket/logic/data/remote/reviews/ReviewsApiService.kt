package com.example.travelmarket.logic.data.remote.reviews

import com.example.travelmarket.logic.data.models.request.reviews.CreateReviewRequest
import com.example.travelmarket.logic.data.models.request.reviews.UpdateReviewRequest
import com.example.travelmarket.logic.data.models.response.reviews.ReviewResponse
import com.example.travelmarket.logic.data.models.response.reviews.ReviewsPaginatedResponse
import retrofit2.Response
import retrofit2.http.*

interface ReviewsApiService {

    @GET("reviews/reviews/")
    suspend fun getReviews(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<ReviewsPaginatedResponse>

    @GET("reviews/reviews/{id}/")
    suspend fun getReviewById(
        @Path("id") id: Int
    ): Response<ReviewResponse>

    @POST("reviews/reviews/")
    suspend fun createReview(
        @Body request: CreateReviewRequest
    ): Response<ReviewResponse>

    @PATCH("reviews/reviews/{id}/")
    suspend fun updateReview(
        @Path("id") id: Int,
        @Body request: UpdateReviewRequest
    ): Response<ReviewResponse>

    @DELETE("reviews/reviews/{id}/")
    suspend fun deleteReview(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("reviews/reviews/my_reviews/")
    suspend fun getMyReviews(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<ReviewsPaginatedResponse>
}
