package com.example.travelmarket.logic.data.remote.destinations

import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DestinationsApiService {

    @GET("destinations/")
    suspend fun getDestinations(
        @Query("country") country: String? = null,
        @Query("continent") continent: String? = null,
        @Query("is_popular") isPopular: Boolean? = null,
        @Query("best_season") bestSeason: String? = null,
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedResponse<DestinationResponse>>
}