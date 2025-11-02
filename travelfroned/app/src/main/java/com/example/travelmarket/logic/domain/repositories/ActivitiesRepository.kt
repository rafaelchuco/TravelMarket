package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Activity

interface ActivitiesRepository {

    suspend fun getActivities(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<PaginatedResponse<Activity>>

    suspend fun getActivityById(id: Int): NetworkResult<Activity>
}