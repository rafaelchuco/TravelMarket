package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import com.example.travelmarket.logic.domain.models.Destination

object DestinationMapper {

    fun toDomain(response: DestinationResponse): Destination {
        return Destination(
            id = response.id,
            name = response.name,
            country = response.country,
            continent = response.continent,
            description = response.description,
            shortDescription = response.shortDescription,
            latitude = response.latitude,
            longitude = response.longitude,
            image = response.image,
            isPopular = response.isPopular,
            bestSeason = response.bestSeason,
            createdAt = response.createdAt
        )
    }

    fun toDomainList(responseList: List<DestinationResponse>): List<Destination> {
        return responseList.map { toDomain(it) }
    }
}