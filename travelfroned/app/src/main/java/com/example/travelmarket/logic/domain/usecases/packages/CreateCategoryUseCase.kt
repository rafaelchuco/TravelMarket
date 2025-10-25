package com.example.travelmarket.logic.domain.usecases.packages

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.packages.CreateCategoryRequest
import com.example.travelmarket.logic.domain.models.PackageCategory
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val repository: PackagesRepository
) {
    suspend operator fun invoke(request: CreateCategoryRequest): NetworkResult<PackageCategory> {
        return repository.createCategory(request)
    }
}
