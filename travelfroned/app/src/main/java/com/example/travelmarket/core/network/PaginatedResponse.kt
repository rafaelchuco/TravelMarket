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
    val results: Any? = null
) {
    fun getItems(): List<T> {
        if (results is List<*>) {
            return results as? List<T> ?: emptyList()
        }

        if (results is Map<*, *>) {
            val map = results as Map<String, Any>
            (map["actividades"] as? List<T>)?.let { return it }
            (map["destinos"] as? List<T>)?.let { return it }
            (map["paquetes"] as? List<T>)?.let { return it }
        }

        return emptyList()
    }
}