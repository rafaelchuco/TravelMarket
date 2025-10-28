package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination

interface DestinationsRepository {

    suspend fun getDestinations(
        country: String? = null,
        continent: String? = null,
        isPopular: Boolean? = null,
        bestSeason: String? = null,
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Destination>>
}