package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.inquiries.InquiryResponse
import com.example.travelmarket.logic.domain.models.Inquiry

object InquiryMapper {
    fun toDomain(response: InquiryResponse): Inquiry {
        return Inquiry(
            id = response.id,
            name = response.name ?: "Sin nombre",
            email = response.email ?: "",
            phone = response.phone,
            subject = response.subject ?: "Sin asunto",
            message = response.message ?: "",
            packageId = response.packageId,
            status = response.status ?: "pending",
            adminResponse = response.adminResponse,
            createdAt = response.createdAt ?: "",
            updatedAt = response.updatedAt
        )
    }

    fun toDomainList(responses: List<InquiryResponse>): List<Inquiry> {
        return responses.map { toDomain(it) }
    }
}
