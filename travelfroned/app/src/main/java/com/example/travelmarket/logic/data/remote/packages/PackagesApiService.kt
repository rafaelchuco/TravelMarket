package com.example.travelmarket.logic.data.remote.packages

import com.example.travelmarket.logic.data.models.request.packages.CreateCategoryRequest
import com.example.travelmarket.logic.data.models.request.packages.CreatePackageRequest
import com.example.travelmarket.logic.data.models.response.packages.CategoriesApiResponse
import com.example.travelmarket.logic.data.models.response.packages.CategoryResponse
import com.example.travelmarket.logic.data.models.response.packages.PackageResponse
import com.example.travelmarket.logic.data.models.response.packages.PackagesApiResponse  // ✅ AGREGADO
import retrofit2.Response
import retrofit2.http.*

interface PackagesApiService {
    // Packages
    @GET("packages/")
    suspend fun list(): Response<PackagesApiResponse>  // ✅ CAMBIADO

    @POST("packages/")
    suspend fun create(@Body body: CreatePackageRequest): Response<PackageResponse>

    @GET("packages/{id}/")
    suspend fun read(@Path("id") id: Long): Response<PackageResponse>

    @PUT("packages/{id}/")
    suspend fun update(
        @Path("id") id: Long,
        @Body body: CreatePackageRequest
    ): Response<PackageResponse>

    @PATCH("packages/{id}/")
    suspend fun partialUpdate(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<PackageResponse>

    @DELETE("packages/{id}/")
    suspend fun delete(@Path("id") id: Long): Response<Unit>

    // Categories
    @GET("packages/categories/")
    suspend fun listCategories(): Response<CategoriesApiResponse>

    @POST("packages/categories/")
    suspend fun createCategory(@Body body: CreateCategoryRequest): Response<CategoryResponse>

    @GET("packages/categories/{id}/")
    suspend fun readCategory(@Path("id") id: Long): Response<CategoryResponse>

    @PUT("packages/categories/{id}/")
    suspend fun updateCategory(
        @Path("id") id: Long,
        @Body body: CreateCategoryRequest
    ): Response<CategoryResponse>

    @PATCH("packages/categories/{id}/")
    suspend fun partialUpdateCategory(
        @Path("id") id: Long,
        @Body body: Map<String, Any?>
    ): Response<CategoryResponse>

    @DELETE("packages/categories/{id}/")
    suspend fun deleteCategory(@Path("id") id: Long): Response<Unit>
}
