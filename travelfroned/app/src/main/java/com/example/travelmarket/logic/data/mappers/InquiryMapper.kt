package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.inquiries.InquiryResponse
import com.example.travelmarket.logic.domain.models.Inquiry

object InquiryMapper {
    fun toDomain(response: InquiryResponse): Inquiry {
        return Inquiry(
            id = response.id,
            userId = response.userId,
            email = response.email ?: "",  // ✅ AGREGADO ?:
            message = response.message ?: "",  // ✅ AGREGADO ?:
            packageId = response.packageId,
            status = response.status ?: "pending",
            createdAt = response.createdAt ?: ""
        )
    }

    fun toDomainList(responses: List<InquiryResponse>): List<Inquiry> {
        return responses.map { toDomain(it) }
    }
}
