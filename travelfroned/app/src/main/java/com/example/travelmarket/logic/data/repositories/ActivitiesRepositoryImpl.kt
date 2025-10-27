package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.mappers.ActivityMapper
import com.example.travelmarket.logic.data.models.response.activities.ActivityResponse
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivitiesRepositoryImpl(
    private val apiService: ActivitiesApiService,
    private val mapper: ActivityMapper = ActivityMapper
) : BaseRepository(), ActivitiesRepository {

    @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
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

                // ✅ Parsear manually porque results es un objeto anidado
                val activityResponses: List<ActivityResponse> = try {
                    val items = paginatedResponse.results

                    // Si son LinkedTreeMaps, convertir a ActivityResponse
                    if (items.isNotEmpty() && items.first() is Map<*, *>) {
                        val gson = Gson()
                        val json = gson.toJson(items)
                        val type = object : TypeToken<List<ActivityResponse>>() {}.type
                        gson.fromJson(json, type)
                    } else {
                        items as List<ActivityResponse>
                    }
                } catch (e: Exception) {
                    android.util.Log.e("ACTIVITIES_REPO", "Error parsing: ${e.message}")
                    emptyList()
                }

                val mappedActivities = mapper.toDomainList(activityResponses)

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
