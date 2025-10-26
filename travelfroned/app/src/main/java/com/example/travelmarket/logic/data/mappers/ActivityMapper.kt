package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.activities.ActivityResponse
import com.example.travelmarket.logic.domain.models.Activity

object ActivityMapper {

    fun toDomain(response: ActivityResponse): Activity {
        return Activity(
            id = response.id,
            name = response.name,
            activityType = response.activityType,
            description = response.description,
            durationHours = response.durationHours,
            difficultyLevel = response.difficultyLevel,
            pricePerPerson = response.pricePerPerson.toDoubleOrNull() ?: 0.0,
            maxGroupSize = response.maxGroupSize,
            image = response.image,
            isActive = response.isActive,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            destinationId = response.destination
        )
    }

    fun toDomainList(responses: List<ActivityResponse>): List<Activity> {
        return responses.map { toDomain(it) }
    }
}