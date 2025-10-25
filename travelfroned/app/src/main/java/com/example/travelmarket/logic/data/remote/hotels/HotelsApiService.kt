package com.example.travelmarket.logic.data.remote.hotels

import com.example.travelmarket.logic.data.models.request.hotels.CreateHotelRequest
import com.example.travelmarket.logic.data.models.response.hotels.HotelResponse
import com.example.travelmarket.logic.data.models.response.hotels.HotelsApiResponse
import retrofit2.Response
import retrofit2.http.*

interface HotelsApiService {
    @GET("hotels/")
    suspend fun list(): Response<HotelsApiResponse>  // ✅ CAMBIADO: ahora devuelve HotelsApiResponse

    @POST("hotels/")
    suspend fun create(@Body body: CreateHotelRequest): Response<HotelResponse>

    @GET("hotels/{id}/")
    suspend fun read(@Path("id") id: Long): Response<HotelResponse>

    @PUT("hotels/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: CreateHotelRequest
    ): Response<HotelResponse>

    @PATCH("hotels/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<HotelResponse>

    @DELETE("hotels/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
