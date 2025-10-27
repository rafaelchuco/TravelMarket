package com.example.travelmarket.logic.data.models.response.hotels

import com.google.gson.annotations.SerializedName

data class HotelsApiResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: HotelsResultWrapper?  // ✅ Cambio
)

// ✅ NUEVO: Wrapper para acceder a "hoteles"
data class HotelsResultWrapper(
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("hoteles") val hoteles: List<HotelResponse>?
)

data class HotelResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("destination") val destination: Any?,
    @SerializedName("address") val address: String?,
    @SerializedName("star_rating") val starRating: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("amenities") val amenities: String?,
    @SerializedName("check_in_time") val checkInTime: String?,
    @SerializedName("check_out_time") val checkOutTime: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("price_per_night") val pricePerNight: String?,
    @SerializedName("total_rooms") val totalRooms: Int?,
    @SerializedName("image") val image: String?,
    @SerializedName("is_active") val isActive: Boolean?
)
