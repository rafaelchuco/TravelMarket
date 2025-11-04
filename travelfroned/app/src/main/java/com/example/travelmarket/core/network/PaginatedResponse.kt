package com.example.travelmarket.core.network

data class PaginatedResponse<T>(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = emptyList()
)

fun <T> PaginatedResponse<T>.getItems(): List<T> = results
