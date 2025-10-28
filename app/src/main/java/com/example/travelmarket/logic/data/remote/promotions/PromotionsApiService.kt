package com.example.travelmarket.logic.data.remote.promotions

import com.example.travelmarket.logic.data.models.response.promotions.CouponsWrapperResponse
import com.example.travelmarket.logic.data.models.response.promotions.PromotionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PromotionsApiService {

    @GET("promotions/coupons/")
    suspend fun getPromotions(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<CouponsWrapperResponse>

    @GET("promotions/coupons/{id}/")
    suspend fun getPromotionById(
        @Path("id") id: Int
    ): Response<PromotionResponse>
}