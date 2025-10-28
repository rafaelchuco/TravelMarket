package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.promotions.PromotionResponse
import com.example.travelmarket.logic.domain.models.Promotion

object PromotionMapper {

    fun toDomain(response: PromotionResponse): Promotion {
        return Promotion(
            id = response.id,
            code = response.code,
            description = response.description,
            discountType = response.discountType,
            discountValue = response.discountValue,
            minPurchaseAmount = response.minPurchaseAmount,
            maxDiscountAmount = response.maxDiscountAmount,
            startDate = response.startDate,
            endDate = response.endDate,
            isActive = response.isActive
        )
    }

    fun toDomainList(responses: List<PromotionResponse>): List<Promotion> {
        return responses.map { toDomain(it) }
    }
}