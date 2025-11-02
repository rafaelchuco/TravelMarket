package com.example.travelmarket.logic.domain.usecases.packages

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.PackageCategory
import com.example.travelmarket.logic.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): NetworkResult<List<PackageCategory>> {
        return repository.getCategories()
    }
}
