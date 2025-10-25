package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.packages.CategoryResponse
import com.example.travelmarket.logic.data.models.response.packages.PackageResponse
import com.example.travelmarket.logic.domain.models.PackageCategory

object PackageMapper {
    fun toDomain(response: PackageResponse): com.example.travelmarket.logic.domain.models.Package? {
        if (response.name.isNullOrEmpty()) {
            return null
        }

        return com.example.travelmarket.logic.domain.models.Package(
            id = response.id,
            title = response.name,
            description = response.shortDescription ?: "Sin descripción",  // ✅ USA short_description
            price = response.priceAdult ?: 0.0,
            durationDays = response.durationDays ?: 0,
            categoryId = 0L,  // No viene en JSON
            destinationId = 0L,  // No viene en JSON
            imageUrl = response.image ?: ""
        )
    }

    fun toDomainList(responses: List<PackageResponse>): List<com.example.travelmarket.logic.domain.models.Package> {
        return responses.mapNotNull { toDomain(it) }
    }

    fun categoryToDomain(response: CategoryResponse): PackageCategory {
        return PackageCategory(
            id = response.id,
            name = response.name ?: "Sin nombre",
            description = response.description ?: "",
            createdAt = response.createdAt ?: ""
        )
    }

    fun categoriesToDomainList(responses: List<CategoryResponse>): List<PackageCategory> {
        return responses.map { categoryToDomain(it) }
    }
}
