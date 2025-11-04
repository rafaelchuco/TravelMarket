package com.example.travelmarket.logic.data.models.response.destinations

import com.google.gson.annotations.SerializedName

data class DestinationsApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: DestinationsResultWrapper?
)

data class DestinationsResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("destinos") val destinos: List<DestinationResponse>?
)
