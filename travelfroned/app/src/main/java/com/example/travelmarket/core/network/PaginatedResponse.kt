package com.example.travelmarket.core.network

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<T>(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: List<T>
)