package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import com.example.travelmarket.logic.domain.models.Destination

object DestinationMapper {
    fun toDomain(response: DestinationResponse): Destination {
        return Destination(
            id = response.id,
            name = response.name ?: "Sin nombre",  // ✅ AGREGADO ?: "Sin nombre"
            country = response.country ?: "",  // ✅ AGREGADO ?: ""
            continent = response.continent ?: "",
            description = response.description ?: "",
            shortDescription = response.shortDescription ?: "",
            latitude = response.latitude ?: "",
            longitude = response.longitude ?: "",
            imageUrl = response.image ?: "",
            isPopular = response.isPopular ?: false,
            bestSeason = response.bestSeason ?: "",
            createdAt = response.createdAt ?: ""
        )
    }

    fun toDomainList(responses: List<DestinationResponse>): List<Destination> {
        return responses.map { toDomain(it) }
    }
}
