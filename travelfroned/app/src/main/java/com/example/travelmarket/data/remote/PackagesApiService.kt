package com.example.travelmarket.data.remote

import com.example.travelmarket.data.models.PackagesApiResponse
import com.example.travelmarket.domain.models.Package
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PackagesApiService {
    @GET("packages/")
    suspend fun getPackages(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 100
    ): Response<PackagesApiResponse>
    
    @GET("packages/{id}/")
    suspend fun getPackageById(@Path("id") id: Long): Response<Package>
}
