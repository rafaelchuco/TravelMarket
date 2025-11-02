package com.example.travelmarket.logic.data.remote.inquiries

import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.data.models.response.inquiries.CreateInquiryApiResponse
import com.example.travelmarket.logic.data.models.response.inquiries.InquiriesApiResponse
import com.example.travelmarket.logic.data.models.response.inquiries.InquiryResponse
import retrofit2.Response
import retrofit2.http.*

interface InquiriesApiService {

    // ✅ Mis consultas (usuario logueado)
    @GET("inquiries/my-inquiries/")
    suspend fun getMyInquiries(): Response<InquiriesApiResponse>

    // ✅ Todas las consultas (SOLO ADMIN)
    @GET("inquiries/")
    suspend fun list(): Response<InquiriesApiResponse>

    // ✅ Crear consulta (público, sin login)
    @POST("inquiries/")
    suspend fun create(@Body body: CreateInquiryRequest): Response<CreateInquiryApiResponse>

    // ✅ Ver una consulta por ID
    @GET("inquiries/{id}/")
    suspend fun read(@Path("id") id: Long): Response<InquiryResponse>

    // ✅ Actualizar consulta (ADMIN)
    @PUT("inquiries/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: UpdateInquiryRequest
    ): Response<InquiryResponse>

    // ✅ Actualización parcial (ADMIN)
    @PATCH("inquiries/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<InquiryResponse>

    // ✅ Eliminar consulta (ADMIN)
    @DELETE("inquiries/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
