package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.packages.CategoryResponse
import com.example.travelmarket.logic.domain.models.PackageCategory

object CategoryMapper {
    fun toDomain(response: CategoryResponse): PackageCategory {
        return PackageCategory(
            id = response.id,
            name = response.name ?: "Sin nombre",  // ✅ AGREGADO ?:
            description = response.description ?: "",
            createdAt = response.createdAt ?: ""
        )
    }

    fun toDomainList(responses: List<CategoryResponse>): List<PackageCategory> {
        return responses.map { toDomain(it) }
    }
}
