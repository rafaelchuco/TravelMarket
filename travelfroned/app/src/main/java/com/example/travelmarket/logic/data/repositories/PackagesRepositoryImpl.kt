package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.PackageMapper
import com.example.travelmarket.logic.data.models.request.packages.CreateCategoryRequest
import com.example.travelmarket.logic.data.models.request.packages.CreatePackageRequest
import com.example.travelmarket.logic.data.remote.packages.PackagesApiService
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
import javax.inject.Inject

class PackagesRepositoryImpl @Inject constructor(
    private val api: PackagesApiService
) : PackagesRepository {

    override suspend fun getPackages(): NetworkResult<List<com.example.travelmarket.logic.domain.models.Package>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                val packages = response.body()!!.results?.mapNotNull { PackageMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(packages)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getPackageById(id: Long): NetworkResult<com.example.travelmarket.logic.domain.models.Package> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                val packageData = PackageMapper.toDomain(response.body()!!)
                if (packageData != null) {
                    NetworkResult.Success(packageData)
                } else {
                    NetworkResult.Error("Invalid package data")
                }
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createPackage(request: CreatePackageRequest): NetworkResult<com.example.travelmarket.logic.domain.models.Package> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                val packageData = PackageMapper.toDomain(response.body()!!)
                if (packageData != null) {
                    NetworkResult.Success(packageData)
                } else {
                    NetworkResult.Error("Invalid package data")
                }
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updatePackage(id: Long, request: CreatePackageRequest): NetworkResult<com.example.travelmarket.logic.domain.models.Package> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                val packageData = PackageMapper.toDomain(response.body()!!)
                if (packageData != null) {
                    NetworkResult.Success(packageData)
                } else {
                    NetworkResult.Error("Invalid package data")
                }
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deletePackage(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCategories(): NetworkResult<List<com.example.travelmarket.logic.domain.models.PackageCategory>> {
        return try {
            val response = api.listCategories()
            if (response.isSuccessful && response.body() != null) {
                val categories = response.body()!!.results?.map { PackageMapper.categoryToDomain(it) } ?: emptyList()
                NetworkResult.Success(categories)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createCategory(request: CreateCategoryRequest): NetworkResult<com.example.travelmarket.logic.domain.models.PackageCategory> {
        return try {
            val response = api.createCategory(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(PackageMapper.categoryToDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteCategory(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.deleteCategory(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
