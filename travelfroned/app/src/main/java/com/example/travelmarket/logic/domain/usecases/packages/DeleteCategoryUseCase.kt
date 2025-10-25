package com.example.travelmarket.logic.domain.usecases.packages

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: PackagesRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.deleteCategory(id)
    }
}
