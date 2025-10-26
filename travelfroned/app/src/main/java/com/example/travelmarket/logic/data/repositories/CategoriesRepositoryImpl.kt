package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.utils.toNetworkResult
import com.example.travelmarket.logic.data.mappers.CategoryMapper
import com.example.travelmarket.logic.data.remote.packages.PackagesApiService
import com.example.travelmarket.logic.domain.models.PackageCategory
import com.example.travelmarket.logic.domain.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: PackagesApiService
) : CategoriesRepository {

    override suspend fun getCategories(): NetworkResult<List<PackageCategory>> {
        return try {
            val response = api.listCategories()
            // ✅ USA toNetworkResult()
            when (val result = response.toNetworkResult()) {
                is NetworkResult.Success -> {
                    val categories = result.data.results?.map { CategoryMapper.toDomain(it) } ?: emptyList()
                    NetworkResult.Success(categories)
                }
                is NetworkResult.Error -> result
                NetworkResult.Loading -> NetworkResult.Loading
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCategoryById(id: Long): NetworkResult<PackageCategory> {
        return try {
            val response = api.readCategory(id)
            // ✅ USA toNetworkResult()
            when (val result = response.toNetworkResult()) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(CategoryMapper.toDomain(result.data))
                }
                is NetworkResult.Error -> result
                NetworkResult.Loading -> NetworkResult.Loading
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
