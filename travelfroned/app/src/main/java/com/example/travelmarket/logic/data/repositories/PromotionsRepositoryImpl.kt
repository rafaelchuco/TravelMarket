package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.mappers.PromotionMapper
import com.example.travelmarket.logic.data.remote.promotions.PromotionsApiService
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.domain.repositories.PromotionsRepository

class PromotionsRepositoryImpl(
    private val apiService: PromotionsApiService,
    private val mapper: PromotionMapper = PromotionMapper
) : BaseRepository(), PromotionsRepository {

    override suspend fun getPromotions(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<PaginatedResponse<Promotion>> {
        val result = executeApiCall {
            apiService.getPromotions(search, ordering, page)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val wrapperResponse = result.data
                val mapped = mapper.toDomainList(wrapperResponse.cupones)

                NetworkResult.Success(
                    PaginatedResponse(
                        count = wrapperResponse.cupones.size,
                        next = null,
                        previous = null,
                        results = mapped  // ✅ Solo los parámetros que existen en TU PaginatedResponse
                    )
                )
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun getPromotionById(id: Int): NetworkResult<Promotion> {
        val result = executeApiCall {
            apiService.getPromotionById(id)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val mapped = mapper.toDomain(result.data)
                NetworkResult.Success(mapped)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}
