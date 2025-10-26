package com.example.travelmarket.logic.data.remote.activities

import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.models.response.activities.ActivityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivitiesApiService {

    @GET("activities/")
    suspend fun getActivities(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedResponse<ActivityResponse>>

    @GET("activities/{id}/")
    suspend fun getActivityById(
        @Path("id") id: Int
    ): Response<ActivityResponse>
}