package com.example.travelmarket.logic.data.remote.destinations

import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.data.models.request.destinations.UpdateDestinationRequest
import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import com.example.travelmarket.logic.data.models.response.destinations.DestinationsApiResponse  // ✅ IMPORTAR
import retrofit2.Response
import retrofit2.http.*

interface DestinationsApiService {
    @GET("destinations/")
    suspend fun list(): Response<DestinationsApiResponse>  // ✅ CAMBIADO

    @POST("destinations/")
    suspend fun create(@Body body: CreateDestinationRequest): Response<DestinationResponse>

    @GET("destinations/{id}/")
    suspend fun read(@Path("id") id: Long): Response<DestinationResponse>

    @PUT("destinations/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: UpdateDestinationRequest
    ): Response<DestinationResponse>

    @PATCH("destinations/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<DestinationResponse>

    @DELETE("destinations/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
