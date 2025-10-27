package com.example.travelmarket.logic.data.models.request.packages

import com.google.gson.annotations.SerializedName

data class CreateCategoryRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?
)
