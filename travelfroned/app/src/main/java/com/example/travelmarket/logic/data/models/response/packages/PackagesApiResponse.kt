package com.example.travelmarket.logic.data.models.response.packages
import com.squareup.moshi.Json

data class PackagesApiResponse(
    @Json(name = "results") val results: List<PackageResponse>?
)
