package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.packages.CategoryResponse
import com.example.travelmarket.logic.data.models.response.packages.PackageResponse
import com.example.travelmarket.logic.domain.models.PackageCategory

object PackageMapper {
    fun toDomain(response: PackageResponse): com.example.travelmarket.logic.domain.models.Package? {
        android.util.Log.d("PACKAGE_MAPPER", "Mapeando: name=${response.name}, price=${response.priceAdult}, days=${response.durationDays}, desc=${response.shortDescription}")

        if (response.name.isNullOrEmpty()) {
            android.util.Log.e("PACKAGE_MAPPER", "Paquete sin nombre, omitiendo")
            return null
        }

        // ✅ Conversión correcta de String a Double
        val price = try {
            response.priceAdult?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            android.util.Log.e("PACKAGE_MAPPER", "Error convirtiendo precio: ${response.priceAdult}")
            0.0
        }

        android.util.Log.d("PACKAGE_MAPPER", "Precio convertido: $price")

        return com.example.travelmarket.logic.domain.models.Package(
            id = response.id,
            title = response.name,
            description = response.shortDescription ?: "Sin descripción",
            price = price,
            durationDays = response.durationDays ?: 0,
            categoryId = 0L,
            destinationId = 0L,
            imageUrl = response.image ?: ""
        )
    }

    fun toDomainList(responses: List<PackageResponse>): List<com.example.travelmarket.logic.domain.models.Package> {
        android.util.Log.d("PACKAGE_MAPPER", "Mapeando lista de ${responses.size} paquetes")
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
