package com.example.travelmarket.logic.domain.usecases.packages

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.packages.CreatePackageRequest
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
import javax.inject.Inject

class CreatePackageUseCase @Inject constructor(
    private val repository: PackagesRepository
) {
    suspend operator fun invoke(request: CreatePackageRequest): NetworkResult<Package> {
        return repository.createPackage(request)
    }
}
