package com.example.travelmarket.logic.data.remote.inquiries

import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.data.models.response.inquiries.InquiryResponse
import com.example.travelmarket.logic.data.models.response.inquiries.InquiriesApiResponse  // ✅ IMPORTAR
import retrofit2.Response
import retrofit2.http.*

interface InquiriesApiService {
    @GET("inquiries/")
    suspend fun list(): Response<InquiriesApiResponse>  // ✅ CAMBIADO

    @POST("inquiries/")
    suspend fun create(@Body body: CreateInquiryRequest): Response<InquiryResponse>

    @GET("inquiries/{id}/")
    suspend fun read(@Path("id") id: Long): Response<InquiryResponse>

    @PUT("inquiries/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: UpdateInquiryRequest
    ): Response<InquiryResponse>

    @PATCH("inquiries/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<InquiryResponse>

    @DELETE("inquiries/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
