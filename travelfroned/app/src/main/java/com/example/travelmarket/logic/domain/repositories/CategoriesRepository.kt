package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.PackageCategory

interface CategoriesRepository {
    suspend fun getCategories(): NetworkResult<List<PackageCategory>>
    suspend fun getCategoryById(id: Long): NetworkResult<PackageCategory>
}


