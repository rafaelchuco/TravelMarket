package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.DestinationMapper
import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.data.models.request.destinations.UpdateDestinationRequest
import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class DestinationsRepositoryImpl @Inject constructor(
    private val api: DestinationsApiService
) : DestinationsRepository {

    @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
    override suspend fun getDestinations(): NetworkResult<List<Destination>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                val paginatedResponse = response.body()!!

                val destinationResponses: List<DestinationResponse> = try {
                    val items = paginatedResponse.results

                    if (items.isNotEmpty() && items.first() is Map<*, *>) {
                        val gson = Gson()
                        val json = gson.toJson(items)
                        val type = object : TypeToken<List<DestinationResponse>>() {}.type
                        gson.fromJson(json, type)
                    } else {
                        items as List<DestinationResponse>
                    }
                } catch (e: Exception) {
                    android.util.Log.e("DESTINATIONS_REPO", "Error parsing: ${e.message}")
                    emptyList()
                }

                val destinations = destinationResponses.map { DestinationMapper.toDomain(it) }
                NetworkResult.Success(destinations)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getDestinationById(id: Long): NetworkResult<Destination> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createDestination(request: CreateDestinationRequest): NetworkResult<Destination> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updateDestination(id: Long, request: UpdateDestinationRequest): NetworkResult<Destination> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteDestination(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
