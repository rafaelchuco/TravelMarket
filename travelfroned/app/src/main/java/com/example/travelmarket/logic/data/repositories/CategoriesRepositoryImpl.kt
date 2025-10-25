package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
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
            if (response.isSuccessful && response.body() != null) {
                val categories = response.body()!!.results?.map { CategoryMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(categories)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun getCategoryById(id: Long): NetworkResult<PackageCategory> {
        return try {
            val response = api.readCategory(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(CategoryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }
}
