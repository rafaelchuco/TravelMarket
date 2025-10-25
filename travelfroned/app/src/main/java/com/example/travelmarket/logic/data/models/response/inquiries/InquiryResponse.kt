package com.example.travelmarket.logic.data.models.response.inquiries

import com.squareup.moshi.Json

// ✅ Wrapper para respuesta paginada
data class InquiriesApiResponse(
    @Json(name = "count") val count: Int?,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<InquiryResponse>?
)

// ✅ Modelo individual
data class InquiryResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "user_id") val userId: Long?,
    @Json(name = "email") val email: String?,
    @Json(name = "message") val message: String?,
    @Json(name = "package_id") val packageId: Long?,
    @Json(name = "status") val status: String?,
    @Json(name = "created_at") val createdAt: String?
)
