package com.example.travelmarket.logic.data.remote.flights

import com.example.travelmarket.logic.data.models.request.flights.CreateFlightRequest
import com.example.travelmarket.logic.data.models.response.flights.FlightResponse
import com.example.travelmarket.logic.data.models.response.flights.FlightsApiResponse  // ✅ IMPORTAR
import retrofit2.Response
import retrofit2.http.*

interface FlightsApiService {
    @GET("flights/")
    suspend fun list(): Response<FlightsApiResponse>  // ✅ CAMBIADO

    @POST("flights/")
    suspend fun create(@Body body: CreateFlightRequest): Response<FlightResponse>

    @GET("flights/{id}/")
    suspend fun read(@Path("id") id: Long): Response<FlightResponse>

    @PUT("flights/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: CreateFlightRequest
    ): Response<FlightResponse>

    @PATCH("flights/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<FlightResponse>

    @DELETE("flights/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
