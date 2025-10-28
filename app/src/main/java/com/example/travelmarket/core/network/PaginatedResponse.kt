package com.example.travelmarket.core.network

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<T>(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    // ✅ results puede ser un array O un objeto anidado
    @SerializedName("results")
    val results: Any? = null,

    // Campos adicionales por si vienen directamente
    @SerializedName("exito")
    val success: Boolean? = null,

    @SerializedName("mensaje")
    val message: String? = null,

    @SerializedName("actividades")
    val actividades: List<T>? = null,

    @SerializedName("destinos")
    val destinos: List<T>? = null,

    @SerializedName("paquetes")
    val paquetes: List<T>? = null,

    @SerializedName("resenas")
    val resenas: List<T>? = null
) {
    // ✅ Función helper MEJORADA
    fun getItems(): List<T> {
        // Intentar obtener de los campos directos primero
        actividades?.let { return it }
        destinos?.let { return it }
        paquetes?.let { return it }
        resenas?.let { return it }

        // Si results es una lista, devolverla
        if (results is List<*>) {
            return results as? List<T> ?: emptyList()
        }

        // Si results es un Map (objeto JSON), buscar dentro
        if (results is Map<*, *>) {
            val map = results as Map<String, Any>

            // Buscar "actividades", "destinos", "paquetes", "resenas" dentro del objeto
            (map["actividades"] as? List<T>)?.let { return it }
            (map["destinos"] as? List<T>)?.let { return it }
            (map["paquetes"] as? List<T>)?.let { return it }
            (map["resenas"] as? List<T>)?.let { return it }
        }

        return emptyList()
    }
}