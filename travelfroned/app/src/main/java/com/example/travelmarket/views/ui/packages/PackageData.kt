package com.example.travelmarket.views.ui.packages

data class Package(
    val id: String,
    val title: String,
    val location: String,
    val duration: String,
    val rating: Double,
    val reviews: Int,
    val price: Double
)

val packages = listOf(
    Package("pkg1", "Tour Machu Picchu 3D/2N", "Cusco", "3 días", 4.9, 342, 850.0),
    Package("pkg2", "Amazonía 4D/3N", "Iquitos", "4 días", 4.8, 218, 720.0)
)