package com.example.travelmarket.logic.data.models.response.inquiries

import com.google.gson.annotations.SerializedName

data class InquiriesApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: InquiriesResultWrapper?  // ✅ Wrapper
)

// ✅ NUEVO: Wrapper interno
data class InquiriesResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("consultas") val consultas: List<InquiryResponse>?  // ✅ consultas o inquiries
)

data class InquiryResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("user_id") val userId: Long?,
    @SerializedName("email") val email: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("package_id") val packageId: Long?,
    @SerializedName("status") val status: String?,
    @SerializedName("created_at") val createdAt: String?
)
