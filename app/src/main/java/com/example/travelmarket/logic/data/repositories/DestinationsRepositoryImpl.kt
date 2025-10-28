package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.DestinationMapper
import com.example.travelmarket.logic.data.models.response.destinations.DestinationResponse
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DestinationsRepositoryImpl(
    private val apiService: DestinationsApiService
) : BaseRepository(), DestinationsRepository {

    override suspend fun getDestinations(
        country: String?,
        continent: String?,
        isPopular: Boolean?,
        bestSeason: String?,
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<List<Destination>> {
        val result = executeApiCall {
            apiService.getDestinations(
                country = country,
                continent = continent,
                isPopular = isPopular,
                bestSeason = bestSeason,
                search = search,
                ordering = ordering,
                page = page
            )
        }

        return when (result) {
            is NetworkResult.Success -> {
                // ✅ Parsear manualmente el array de destinos
                val destinationResponses: List<DestinationResponse> = try {
                    val items = result.data.getItems()

                    // Si getItems() devolvió LinkedTreeMaps, convertir a DestinationResponse
                    if (items.isNotEmpty() && items.first() is Map<*, *>) {
                        val gson = Gson()
                        val json = gson.toJson(items)
                        val type = object : TypeToken<List<DestinationResponse>>() {}.type
                        gson.fromJson(json, type)
                    } else {
                        items as List<DestinationResponse>
                    }
                } catch (e: Exception) {
                    android.util.Log.e("DESTINATIONS_REPO", "Error parsing destinations: ${e.message}")
                    emptyList()
                }

                val destinations = DestinationMapper.toDomainList(destinationResponses)
                NetworkResult.Success(destinations)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}