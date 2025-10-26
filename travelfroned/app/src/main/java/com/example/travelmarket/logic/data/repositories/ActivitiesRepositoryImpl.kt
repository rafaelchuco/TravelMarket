package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.mappers.ActivityMapper
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository

class ActivitiesRepositoryImpl(
    private val apiService: ActivitiesApiService,
    private val mapper: ActivityMapper = ActivityMapper
) : BaseRepository(), ActivitiesRepository {

    override suspend fun getActivities(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<PaginatedResponse<Activity>> {
        val result = executeApiCall {
            apiService.getActivities(search, ordering, page)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val paginatedResponse = result.data
                val mappedActivities = mapper.toDomainList(paginatedResponse.results)

                NetworkResult.Success(
                    PaginatedResponse(
                        count = paginatedResponse.count,
                        next = paginatedResponse.next,
                        previous = paginatedResponse.previous,
                        results = mappedActivities
                    )
                )
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun getActivityById(id: Int): NetworkResult<Activity> {
        val result = executeApiCall {
            apiService.getActivityById(id)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val activityResponse = result.data
                val mappedActivity = mapper.toDomain(activityResponse)
                NetworkResult.Success(mappedActivity)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}