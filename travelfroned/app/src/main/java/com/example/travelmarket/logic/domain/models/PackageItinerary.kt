package com.example.travelmarket.logic.domain.models

/**
 * PackageItinerary - Modelo de itinerario de paquete
 */
data class PackageItinerary(
    val id: Long,
    val packageId: Long,
    val day: Int,
    val title: String,
    val description: String,
    val activities: List<String>? = null,
    val meals: String? = null,
    val accommodation: String? = null
)
