package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Promotion

interface PromotionsRepository {

    suspend fun getPromotions(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<PaginatedResponse<Promotion>>

    suspend fun getPromotionById(id: Int): NetworkResult<Promotion>
}