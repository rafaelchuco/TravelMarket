package com.example.travelmarket.logic.data.models.response.inquiries

import com.google.gson.annotations.SerializedName

// ✅ Respuesta cuando obtienes una lista de inquiries
data class InquiriesApiResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("consultas") val consultas: List<InquiryResponse>?
)

// ✅ Respuesta de un inquiry individual
data class InquiryResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("subject") val subject: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("package") val packageId: Long?,
    @SerializedName("status") val status: String?,
    @SerializedName("admin_response") val adminResponse: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

// ✅ Respuesta al crear un inquiry
data class CreateInquiryApiResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("consulta") val consulta: InquiryResponse?
)
