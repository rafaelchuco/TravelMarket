package com.example.travelmarket.logic.domain.usecases.packages

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
import javax.inject.Inject

class GetPackagesUseCase @Inject constructor(
    private val repository: PackagesRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Package>> {
        return repository.getPackages()
    }
}
