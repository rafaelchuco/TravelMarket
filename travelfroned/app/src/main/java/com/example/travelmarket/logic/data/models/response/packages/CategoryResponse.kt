package com.example.travelmarket.logic.data.models.response.packages

import com.google.gson.annotations.SerializedName

// ✅ Categories NO usa paginación estándar
data class CategoriesApiResponse(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("categorias") val categorias: List<CategoryResponse>?  // ✅ Cambio: "categorias" en lugar de "results"
)

data class CategoryResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?,  // ✅ Agregado: icon
    @SerializedName("created_at") val createdAt: String?
)
